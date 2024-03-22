package servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by johnny on 2016/8/28.
 * servlet listener
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {
	
	//http://blog.csdn.net/zjf280441589/article/details/51344746
    public ContextLoaderListener(WebApplicationContext context) {
        super();
    }


    /**
     * Initialize the root web application context.
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        initWebApplicationContext(event.getServletContext());
    }


    /**
     * Close the root web application context.
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        closeWebApplicationContext(event.getServletContext());
        ContextCleanupListener contextCleanupListener = new ContextCleanupListener();
//        ContextCleanupListener.cleanupAttributes(event.getServletContext());
    }

}
