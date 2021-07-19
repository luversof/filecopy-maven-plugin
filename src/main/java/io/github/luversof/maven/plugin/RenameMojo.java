package io.github.luversof.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;


/**
 * rename file when generate source
 * 
 * @author bluesky
 *
 */
@Mojo(name = "rename", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class RenameMojo extends AbstractMojo {

	@Component
	private MavenProject project;
	
	@Parameter(required = false)
	private RenameInfo renameInfo;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (renameInfo == null) {
			getLog().info("renameInfo is null");
			return;
		}
		
		if (renameInfo.getRenameFileInfos() == null) {
			getLog().info("renameFileInfoList is null");
			return;
		}
		
		for (RenameFileInfo renameFileInfo : renameInfo.getRenameFileInfos()) {
			getLog().info("target renameFileInfo : " + renameFileInfo);
			findFile(renameFileInfo, project.getBasedir());
		}
	}

	public void findFile(RenameFileInfo renameFileInfo, File file) {
		var regex = Pattern.compile(renameFileInfo.getSourceRegex());
		var list = file.listFiles();
		if (list != null)
			for (var fil : list) {
				if (fil.isDirectory()) {
					findFile(renameFileInfo, fil);
					continue;
				} 
				var matcher = regex.matcher(fil.getName());
				if (matcher.matches()) {
					getLog().debug("matches path : " + fil.getPath());
					if (renameFileInfo.getTargetRegex() == null) {
						getLog().debug("targetRegex is null");
					} else {
						var targetFileName = matcher.replaceAll(renameFileInfo.getTargetRegex());
						Path source = Paths.get(fil.getPath());
						Path target = Paths.get(fil.getParent(), targetFileName);
						getLog().info("copy file : " + fil.getPath() + " to " + targetFileName);
						try {
							Files.copy(source,  target, StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e) {
							getLog().error(e);
						}
					}
				}
			}
	}
}
