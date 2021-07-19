# rename-maven-plugin

Plugin for copying specific files at build time

```pom.xml
<plugin>
    <groupId>io.github.luversof</groupId>
    <artifactId>rename-maven-plugin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <executions>
        <execution>
            <id>rename</id>
            <phase>generate-sources</phase>
            <goals>
                <goal>rename</goal>
            </goals>
            <configuration>
                <renameInfo>
                    <renameFileInfos>
                        <renameFileInfo>
                            <sourceRegex>^([a-z]*)(_zh-TW.properties)$</sourceRegex>
                            <targetRegex>$1_zh.properties</targetRegex>
                        </renameFileInfo>
                    </renameFileInfos>
                </renameInfo>
            </configuration>
        </execution>
    </executions>
</plugin>
```
