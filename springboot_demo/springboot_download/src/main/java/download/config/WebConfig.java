package download.config;

import download.aop.FileFormatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Author: Johnny
 * Date: 2017/4/28
 * Time: 14:50
 */
@Configuration
@ComponentScan(basePackages = {"download.web"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private FileFormatInterceptor fileFormatInterceptor;

    @Value("${swagger.show}")
    private boolean swaggerShow;

    /**
     * SpringMVC configuration
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    /**
     * register interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fileFormatInterceptor);
    }

    /**
     * configuration for loading static resources
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");

        if (this.swaggerShow) {
            System.out.println(swaggerShow);
            System.out.println("tets");
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");

            super.addResourceHandlers(registry);
        }
    }

}
