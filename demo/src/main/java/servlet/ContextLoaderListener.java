package servlet;

import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by wajian on 2016/8/28.
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
        contextCleanupListener.cleanupAttributes(event.getServletContext());
    }

}
