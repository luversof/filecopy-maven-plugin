# filecopy-maven-plugin

Plugin for copying specific files at build time

```pom.xml
<plugin>
    <groupId>io.github.luversof</groupId>
    <artifactId>filecopy-maven-plugin</artifactId>
    <version>1.0.0</version>
    <executions>
        <execution>
            <id>rename</id>
            <phase>generate-sources</phase>
            <goals>
                <goal>filecopy</goal>
            </goals>
            <configuration>
                <fileInfos>
                    <fileInfo>
                        <sourceRegex>^([a-z]*)(_zh-TW.properties)$</sourceRegex>
                        <targetRegex>$1_zh.properties</targetRegex>
                    </fileInfo>
                </fileInfos>
            </configuration>
        </execution>
    </executions>
</plugin>
```
