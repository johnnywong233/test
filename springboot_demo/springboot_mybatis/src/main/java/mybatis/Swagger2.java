package mybatis;

import mybatis.model.User;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

//@Configuration
@EnableSwagger2
//TODO: not work for my case
//https://stackoverflow.com/questions/32449612/spring-boot-springbox-swagger-error
//@Profile("!test")
public class Swagger2 {

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开
     * （访问页面就可以看到效果了）
     */
    @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).ignoredParameterTypes(User.class)
                .pathMapping("")
                .select()
                .apis(RequestHandlerSelectors.basePackage("mybatis"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("SpringBoot mybatis demo",
                "Spring boot + swagger + mybatis + druid",
                "1.0",
                "NO terms of service",
                new Contact("johnny", "https://blog.csdn.net/lonelymanontheway", "1224017485@qq.com"),
                "johnny",
                "johnny-wong@qq.com",
                new ArrayList()
        );
    }
}