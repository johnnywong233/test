package mail.servlet;

import mail.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wajian on 2016/8/9.
 */
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 3202117956537528245L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req , resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        UserService service = new UserService();
        if("register".equals(action)) {
            //register
            String email = req.getParameter("email");
            service.processRegister(email);

            req.getRequestDispatcher("register_success.jsp").forward(req , resp);
        } else if("activate".equals(action)) {
            //validate
            String email = req.getParameter("email");
            String validateCode = req.getParameter("validateCode");

            try {
                service.processActivate(email , validateCode);
                req.getRequestDispatcher("activate_success.jsp").forward(req , resp);
            } catch (ServiceException e) {
                req.setAttribute("message" , e.getMessage());
                req.getRequestDispatcher("activate_failure.jsp").forward(req , resp);
            }

        }
    }
}
