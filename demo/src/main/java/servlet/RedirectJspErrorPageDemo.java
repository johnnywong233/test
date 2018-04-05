package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by johnny on 2016/8/29.
 */
@WebServlet(name = "RedirectJspErrorPageDemo", urlPatterns = "/servlet/RedirectJspErrorPageDemo")
public class RedirectJspErrorPageDemo extends HttpServlet {

    //TODO:DEBUG
    private static final long serialVersionUID = -4007800837325728749L;

    protected void sendErrorRedirect(HttpServletRequest request,
                                     HttpServletResponse response, String errorPageURL,
                                     Throwable e)
            throws ServletException, IOException {
        request.setAttribute("javax.servlet.jsp.jspException", e);
        getServletConfig().getServletContext().
                getRequestDispatcher(errorPageURL).forward(request,
                response);
    }

    //	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // do something
            System.out.println("for test");
        } catch (Exception ex) {
            try {
                sendErrorRedirect(request, response, "ErrorPage.jsp", ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
