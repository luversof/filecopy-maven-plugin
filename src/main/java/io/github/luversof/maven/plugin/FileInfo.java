package io.github.luversof.maven.plugin;

import lombok.Data;

@Data
public class FileInfo {
	
	private String sourceRegex;
	
	private String targetRegex;

}