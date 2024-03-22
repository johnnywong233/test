package servlet;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by johnny on 2016/8/28.
 */
@Slf4j
@WebListener
@WebServlet(name = "ListenerDemo", urlPatterns = {"/ListenerDemo"})
public class ListenerDemo implements ServletRequestListener {
    /*
     * http://blog.csdn.net/zjf280441589/article/details/51344746
     * calculate the time consumed of a HTTP Request
     */
    private static final String START = "Start";

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        request.setAttribute(START, System.nanoTime());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        long start = (Long) request.getAttribute(START);
        long ms = (System.nanoTime() - start) / 1000;
        String uri = request.getRequestURI();
        log.info(String.format("time token to execute %s : %s ms", uri, ms));
    }
}
