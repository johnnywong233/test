package springfox;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Author: Johnny
 * Date: 2016/12/14
 * Time: 21:30
 */
@RestController
@RequestMapping("/user")
@EnableSwagger2
public class SwaggerDemoController {
    @RequestMapping(value = "/demo2.do")
    String getDemo() {
        return "Hello World \n" + "Spring boot3";
    }

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        ApiInfo apiInfo = new ApiInfo("sample of springboot", "sample of springboot", "", "", "", "", "");
        Docket docket = new Docket(DocumentationType.SWAGGER_2).select().paths(regex("/user/.*")).build()
                .apiInfo(apiInfo).useDefaultResponseMessages(false);
        return docket;
    }
}
