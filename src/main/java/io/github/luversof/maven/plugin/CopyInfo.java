package io.github.luversof.maven.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import lombok.Data;

@Data
public class CopyInfo {

	@Parameter(defaultValue = "/src/main")
	private String basePath = "/src/main";

}