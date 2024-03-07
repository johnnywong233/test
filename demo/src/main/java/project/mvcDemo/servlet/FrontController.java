package project.mvcDemo.servlet;

import project.mvcDemo.action.Action;
import project.mvcDemo.action.ActionResult;
import project.mvcDemo.action.ResultContent;
import project.mvcDemo.action.ResultType;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"*.do"}, loadOnStartup = 0,
        initParams = {
                @WebInitParam(name = "packagePrefix", value = "com.lovo.action."),
                @WebInitParam(name = "jspPrefix", value = "/WEB-INF/jsp/"),
                @WebInitParam(name = "actionSuffix", value = "Action")
        }
)
@MultipartConfig
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_PACKAGE_NAME = "com.lovo.action.";        // 默认的Action类的包名前缀
    private static final String DEFAULT_ACTION_NAME = "Action";            // 默认的Action类的类名后缀
    private static final String DEFAULT_JSP_PATH = "/WEB-INF/jsp";            // 默认的JSP文件的路径

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        String contextPath = req.getContextPath() + "/";
        // 获得请求的小服务路径
        String servletPath = req.getServletPath();
        // 从servletPath中去掉开头的斜杠和末尾的.do就是要执行的动作(Action)的名字
        int start = 1;    // 去掉第一个字符斜杠从第二个字符开始
        int end = servletPath.lastIndexOf(".do");    // 找到请求路径的后缀.do的位置
        String actionName = end > start ? servletPath.substring(start, end) + DEFAULT_ACTION_NAME : "";
        String actionClassName = DEFAULT_PACKAGE_NAME + actionName.substring(0, 1).toUpperCase() + actionName.substring(1);
        try {
            // 通过反射来创建Action对象并调用
            Action action = (Action) Class.forName(actionClassName).newInstance();
            // 执行多态方法execute得到ActionResult
            ActionResult result = action.execute(req, resp);
            ResultType resultType = result.getResultType();// 结果类型
            ResultContent resultContent = result.getResultContent();// 结果内容
            // 根据ResultType决定如何处理
            switch (resultType) {
                case Forward: // 跳转
                    req.getRequestDispatcher(
                            DEFAULT_JSP_PATH + resultContent.getUrl()).forward(req,
                            resp);
                    break;
                case Redirect: // 重定向
                    resp.sendRedirect(resultContent.getUrl());
                    break;
                case Ajax: // Ajax
                    PrintWriter pw = resp.getWriter();
                    pw.println(resultContent.getJson());
                    pw.close();
                    break;
                case Chain:
                    req.getRequestDispatcher(contextPath + resultContent.getUrl())
                            .forward(req, resp);
                    break;
                case RedirectChain:
                    resp.sendRedirect(contextPath + resultContent.getUrl());
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
