package johnny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMailApplication.class, args);
	}
}
