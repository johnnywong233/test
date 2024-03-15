package websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Johnny
 * Date: 2017/7/25
 * Time: 0:14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * MVC view-controller config
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //添加一个请求映射地址为/index，返回对应视图页面为webSocket
        registry.addViewController("/index").setViewName("/webSocket");
    }
}
