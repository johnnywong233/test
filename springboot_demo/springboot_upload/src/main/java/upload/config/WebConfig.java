package upload.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import upload.aop.FileFormatInterceptor;

/**
 * Author: Johnny
 * Date: 2017/4/28
 * Time: 14:50
 */
@Configuration
//@ComponentScan(basePackages = {"upload.web"})
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private FileFormatInterceptor fileFormatInterceptor;

    /**
     * register interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fileFormatInterceptor);
    }

    /**
     * configuration for loading static resources
     * https://my.oschina.net/junko2013/blog/1823475
     * TODO
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
//        super.addResourceHandlers(registry);
    }

}
