package johnny.util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    //ThreadLocal Session Map
    private static final ThreadLocal<Session> SESSION_MAP = new ThreadLocal<>();
    private static final SessionFactory SESSION_FACTORY;
    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);

    static {
        try {
            LOGGER.debug("HibernateUti.static - loading configure");
            SESSION_FACTORY = new Configuration().configure()
                    .buildSessionFactory();
            LOGGER.debug("HibernateUtil.static - end");
        } catch (Throwable ex) {
            ex.printStackTrace();
            LOGGER.error("HibernateUti error : ExceptionInInitializerError");
            throw new ExceptionInInitializerError(ex);
        }
    }

    private HibernateUtil() {
    }

    public static Session getSession() throws HibernateException {
        Session session = SESSION_MAP.get();
        if (session == null) {
            session = SESSION_FACTORY.openSession();
            SESSION_MAP.set(session);
        }
        return session;
    }

    public static void closeSession() throws HibernateException {
        Session session = SESSION_MAP.get();
        SESSION_MAP.set(null);
        SESSION_MAP.remove();
        if (session != null) {
            session.close();
        }
    }

}
