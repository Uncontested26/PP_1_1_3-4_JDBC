package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private static Util util = new Util();
    SessionFactory factory = util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE  IF NOT EXISTS USERS (" +
                    "Id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ," +
                    " name VARCHAR(50) NOT NULL," +
                    " lastName VARCHAR(50) NOT NULL," +
                    " age INT(3) NOT NULL )").executeUpdate();
            session.getTransaction().commit();
        }


    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();

        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {


        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            list = session.createQuery("from User ").list();
            session.getTransaction().commit();

        }
        System.out.println(list);
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        }

    }
}
