package johnny.dao;

import johnny.model.User;
import johnny.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao {

    public void save(User user) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public User findByEmail(String email) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User user = (User) session.createQuery("select u from User u where u.email=?")
                .setParameter(0, email)
                .uniqueResult();
        session.getTransaction().commit();
        HibernateUtil.closeSession();
        return user;
    }

    public void updateUserStatus(Long id, int status) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createQuery("update User u set u.status=? where u.id=?")
                    .setParameter(0, status)
                    .setParameter(1, id)
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

}
