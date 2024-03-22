package project.mvcDemo.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 处理用户请求的控制器接口
 */
public interface Action {
    ActionResult execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException;
}
