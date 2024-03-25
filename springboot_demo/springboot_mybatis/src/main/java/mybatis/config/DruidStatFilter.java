package mybatis.config;

import com.alibaba.druid.support.jakarta.WebStatFilter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源
        })
/*
 * Druid拦截器，用于查看Druid监控
 * http://localhost:8081/druid/sql.html
 */
public class DruidStatFilter extends WebStatFilter {
}