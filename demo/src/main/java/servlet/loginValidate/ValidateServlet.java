// Fig. 5.6_01_02:  ValidateServlet.java
// 校验认证码的Servlet

package servlet.loginValidate;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ValidateServlet extends HttpServlet
{
	private static final long serialVersionUID = 3391706760785590707L;

	//处理客户端提交数据的 "Post" 请求
    protected void doPost(HttpServletRequest request, HttpServletResponse
        response)throws ServletException, IOException
    {
        //发送 XHTML 格式的页面给客户端
        response.setContentType("text/html;charset=utf-8");

        // set page no-cache
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 获得用户输入的验证码
        String validateCode = request.getParameter("validateCode");

        // 获取保存在session中的验证码
        HttpSession session = request.getSession();
        String codeNumbers = (String)session.getAttribute("codeNumbers");

        String url = "/validate.html";

        if (codeNumbers == null)
        {
            response.sendRedirect(url);
            return ;
        }

        PrintWriter out = response.getWriter();

        // 开始生成 XHTML 文档
        out.println("<?xml version = \"1.0\"?>");

        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD " + 
			"XHTML 1.0 Strict//EN\" \"http://www.w3.org" + 
			"/TR/xhtml1/DTD/xhtml1-strict.dtd\">");

        out.println("<html xmlns = \"http://www.w3.org/1999/xhtml\">");

        out.println("<head>");
        out.println("<title>校验认证码的Servlet</title>");
        out.println("</head>");

        out.println("<body>");

        // 校验用户输入的验证码和session中的验证码是否相同
        if (codeNumbers.equals(validateCode))
        {
            out.println(
                "<h1><font color=\"green\">输入相同,校验成功</font></h1> ");
        }
        else
        {
            out.println("<h1><font color=\"red\">Input error, validate fail</font> <br>");
            out.println("<p>Please input again<a href=\"" + url +
				"\">Input code</a><h1></p>");
        }

        out.println("</body>");

        // 结束 XHTML 文档
        out.println("</html>");
        out.close();
    }

    // 处理客户端提交数据的 "get" 请求, 和doPost一样
    protected void doGet(HttpServletRequest request, HttpServletResponse
        response)throws ServletException, IOException
    {
        doPost(request, response);
    }
}