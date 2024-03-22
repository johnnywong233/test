package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

/**
 * Created by johnny on 2016/8/28.
 */
@WebServlet(name = "CookieDemo", urlPatterns = {"/CookieDemo"})
public class CookieDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastAccessTime = "lastAccessTime";
        Cookie[] cookies = request.getCookies();
        Cookie latCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(lastAccessTime)) {
                    latCookie = cookie;
                    break;
                }
            }
        }

        //already accessed
        if (latCookie != null) {
            printResponse("You have visited in the last time:" + latCookie.getValue(), response);
            latCookie.setValue(new Date().toString());
        } else {
            printResponse("This is your first visit", response);
            latCookie = new Cookie(lastAccessTime, new Date().toString());
        }

        response.addCookie(latCookie);
    }

    //http://blog.csdn.net/zjf280441589/article/details/51302782
    private void printResponse(String data, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().print("<H1>" + data + "</H1>");
    }
}
