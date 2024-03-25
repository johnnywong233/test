package mybatis.config;

import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;

import com.alibaba.druid.support.jakarta.StatViewServlet;

/**
 * Druid Servlet
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = "127.0.0.1,192.168.1.126"),// IP白名单 (没有配置或者为空，则允许所有访问)
                @WebInitParam(name = "loginUsername", value = "johnny"),
                @WebInitParam(name = "loginPassword", value = "johnny"),
                @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
        })
public class DruidStatViewServlet extends StatViewServlet {
}