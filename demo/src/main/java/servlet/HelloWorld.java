package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet(name = "CalendarServlet", urlPatterns = {"/CalendarServlet"})
public class HelloWorld extends HttpServlet {
    private static final long serialVersionUID = -5460484963132169816L;
    private String message;

    @Override
    public void init() throws ServletException {
        message = "Johnny, 你好";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    @Override
    public void destroy() {
        // do nothing
    }

}
