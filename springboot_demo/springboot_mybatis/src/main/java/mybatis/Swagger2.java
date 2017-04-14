package mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import mybatis.model.User;

@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开
     * （访问页面就可以看到效果了）
     */
    @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).ignoredParameterTypes(User.class)
                .pathMapping("")//
                .select()
                .apis(RequestHandlerSelectors.basePackage("mybatis"))//base Package
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("SpringBoot mybatis demo",//big title
                "Spring boot + swagger + mybatis + druid",//small title
                "1.0",//version
                "NO terms of service",
                "johnny",//author
                "johnny",//链接显示文字
                "johnny-wong@qq.com"//link
        );
    }
}