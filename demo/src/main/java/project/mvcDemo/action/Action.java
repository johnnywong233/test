package project.mvcDemo.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 处理用户请求的控制器接口
 */
public interface Action {
    ActionResult execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;
}
