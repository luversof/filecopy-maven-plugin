package io.github.luversof.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
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
@Mojo(name = "filecopy", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class FileCopyMojo extends AbstractMojo {

	@Component
	private MavenProject project;
	
	@Parameter(required = false)
	private CopyInfo copyInfo;
	
	@Parameter(required = false)
	private List<FileInfo> fileInfos;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (copyInfo == null) {
			copyInfo = new CopyInfo();
		}
		
		if (fileInfos == null) {
			getLog().info("renameFileInfoList is null");
			return;
		}
		
		var basePath = new File(project.getBasedir().getPath() + copyInfo.getBasePath());
		for (FileInfo fileInfo : fileInfos) {
			getLog().info("target fileInfo : " + fileInfo);
			findFile(fileInfo, basePath);
		}
	}

	public void findFile(FileInfo fileInfo, File file) {
		var regex = Pattern.compile(fileInfo.getSourceRegex());
		var list = file.listFiles();
		if (list != null)
			for (var fil : list) {
				if (fil.isDirectory()) {
					findFile(fileInfo, fil);
					continue;
				} 
				
				var matcher = regex.matcher(fil.getName());
				if (matcher.matches()) {
					getLog().debug("matches path : " + fil.getPath());
					if (fileInfo.getTargetRegex() == null) {
						getLog().debug("targetRegex is null");
					} else {
						var targetFileName = matcher.replaceAll(fileInfo.getTargetRegex());
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
