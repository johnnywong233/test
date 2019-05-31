package jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Author: Johnny
 * Date: 2017/4/17
 * Time: 22:56
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
