package project.mvcDemo.util;

public class DbSessionFactory {
    private static final ThreadLocal<DbSession> THREAD_LOCAL = new ThreadLocal<>();

    private DbSessionFactory() {
        throw new AssertionError();
    }

    /**
     * 打开会话
     *
     * @return DbSession对象
     */
    public static DbSession openSession() throws DbSessionException {
        DbSession session = THREAD_LOCAL.get();
        if (session == null) {
            session = new DbSession();
            THREAD_LOCAL.set(session);
        }
        session.open();
        return session;
    }

    /**
     * 关闭会话
     */
    public static void closeSession() throws DbSessionException {
        DbSession session = THREAD_LOCAL.get();
        THREAD_LOCAL.set(null);
        THREAD_LOCAL.remove();
        if (session != null) {
            session.close();
        }
    }
}
