package johnny.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import johnny.service.ServiceException;
import johnny.service.UserService;

import java.io.IOException;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        UserService service = new UserService();
        if ("register".equals(action)) {
            //register
            String email = req.getParameter("email");
            service.processRegister(email);
            req.getRequestDispatcher("register_success.jsp").forward(req, resp);
        } else if ("activate".equals(action)) {
            //activate
            String email = req.getParameter("email");
            String validateCode = req.getParameter("validateCode");
            try {
                service.processActivate(email, validateCode);
                req.getRequestDispatcher("activate_success.jsp").forward(req, resp);
            } catch (ServiceException e) {
                req.setAttribute("message", e.getMessage());
                req.getRequestDispatcher("activate_failure.jsp").forward(req, resp);
            }
        }
    }

}