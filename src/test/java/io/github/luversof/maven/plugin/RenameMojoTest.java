package io.github.luversof.maven.plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class RenameMojoTest {

	@Test
	void test() {
		Pattern regex = Pattern.compile("^([a-z]*)(\\_zh\\-TW\\.properties)$");
		String fileName = "test_zh-TW.properties";
		Matcher regexMatcher = regex.matcher(fileName);
		System.out.println(regexMatcher.groupCount());
		System.out.println(regexMatcher.replaceAll("$1_zh.properties"));
	}

}
