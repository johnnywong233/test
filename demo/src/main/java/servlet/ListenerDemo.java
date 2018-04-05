package servlet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by wajian on 2016/8/28.
 */
@WebListener
@WebServlet(name = "ListenerDemo", urlPatterns = {"/ListenerDemo"})
public class ListenerDemo implements ServletRequestListener {
    /*
     * http://blog.csdn.net/zjf280441589/article/details/51344746
     * calculate the time consumed of a HTTP Request
     */
    private static final Logger LOGGER = Logger.getLogger("ListenerDemo");
    //TODO


    private static final String START = "Start";

    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        request.setAttribute(START, System.nanoTime());
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        long start = (Long) request.getAttribute(START);
        long ms = (System.nanoTime() - start) / 1000;
        String uri = request.getRequestURI();
        LOGGER.info(String.format("time token to execute %s : %s ms", uri, ms));
    }
}
