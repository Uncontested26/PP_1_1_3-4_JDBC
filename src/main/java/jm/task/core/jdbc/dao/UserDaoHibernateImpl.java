package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.SystemException;
import javax.transaction.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private SessionFactory factory = getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE  IF NOT EXISTS USERS (" +
                    "Id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ," +
                    " name VARCHAR(50) NOT NULL," +
                    " lastName VARCHAR(50) NOT NULL," +
                    " age INT(3) NOT NULL )").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        }catch (Exception e){
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e){
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> list = new ArrayList<>();

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            list = session.createQuery("from User ").list();
            session.getTransaction().commit();
        }catch (Exception e){
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println(list);
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
