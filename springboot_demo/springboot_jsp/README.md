## springboot integrated with JSP
How: 
 

## springboot apidoc
Firstly install node.js, then install apidoc.  
install node.js is easy: download .msi file for the windows system, next to the end;  

## springboot actuator to get git commit info
How:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<build>
        <plugins>
            <!--NOTED: do not use 2.2.2 version, because when execute
             plugin:git-commit-id:revision will throw Error injecting:
             pl.project13.maven.git.GitCommitIdMojo
            java.lang.NoClassDefFoundError: com/google/common/base/Function-->
            <plugin>
                <groupId>pl.project13.maven</groupId>
                <artifactId>git-commit-id-plugin</artifactId>
                <version>2.1.15</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>revision</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <dotGitDirectory>../../.git</dotGitDirectory>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

## spring event