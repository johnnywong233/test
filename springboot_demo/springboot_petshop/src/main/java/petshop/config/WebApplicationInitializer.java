package petshop.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Author: Johnny
 * Date: 2017/7/9
 * Time: 13:09
 */
public interface WebApplicationInitializer {
    void onStartup(ServletContext servletContext) throws ServletException;
}
