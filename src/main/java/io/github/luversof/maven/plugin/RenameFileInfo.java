package io.github.luversof.maven.plugin;

import lombok.Data;

@Data
public class RenameFileInfo {
	
	private String sourceRegex;
	
	private String targetRegex;

}