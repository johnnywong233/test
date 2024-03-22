package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by johnny on 2016/8/29.
 */
@WebServlet(name = "RedirectJspErrorPageDemo", urlPatterns = "/servlet/RedirectJspErrorPageDemo")
public class RedirectJspErrorPageDemo extends HttpServlet {
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
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // do something
            System.out.println("for test");
        } catch (Exception ex) {
            try {
                sendErrorRedirect(request, response, "index.jsp", ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
