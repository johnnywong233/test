package shrio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    //参数里VM参数设置为：-javaagent:.\lib\springloaded-1.2.4.RELEASE.jar -noverify
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
