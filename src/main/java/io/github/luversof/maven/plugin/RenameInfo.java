package io.github.luversof.maven.plugin;

import java.util.List;

import org.apache.maven.plugins.annotations.Parameter;

import lombok.Data;

@Data
public class RenameInfo {

	@Parameter(defaultValue = "src/main/")
	private String basePath = "src/main/";

	private List<RenameFileInfo> renameFileInfos;
	
}