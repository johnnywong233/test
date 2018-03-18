## springboot integrated with JSP
How: 
for the application.properties, add this:
```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```
for the main class, add:
```java
public class App extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}
}
```
and for the pom.xml, just add these two for the jsp file:
```xml
<dependencies>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
will be OK.  
And one more vital thing in my case, remember to packaging the project in a **war**.

## springboot apidoc
Firstly install node.js, then install apidoc.  
install node.js is easy: download .msi file for the windows system, next to the end;  
install apidoc is not that easy for me; anyway finally succeed: 
```
npm install api -g
```
then go this ```test/springboot_demo``` folder, run:
```
apidoc -i springboot_jsp/ -o springboot_jsp/apidoc/
```
will get info 'info: Done.' open the index.html to check it.  
To be noted, that -i is for the input folder, and -o is for the output folder.

## springboot actuator to get git commit info
How:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
```xml
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
using git-commit-id plugin to run command: git-commit-id:revision, double click;
check run message like:
```
[INFO] pl.project13.maven.git.log.MavenLoggerBridge - dotGitDirectory /Users/wangjian/GitHub/test/.git
[INFO] pl.project13.maven.git.log.MavenLoggerBridge - git.build.user.name 王健
...
```
check the folder under target, see git.properties file;

start app, check /info: 
```curl localhost:8080/info```

and config in application.properties:
```
management.info.git.mode=full
```
to get a full git commit info through actuator.

## spring event
annotation @EventListener and abstract class ApplicationEvent