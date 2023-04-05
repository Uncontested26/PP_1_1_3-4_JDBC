package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = util.getConnection();
        String sql = "CREATE TABLE  IF NOT EXISTS USERS (" +
                "Id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ," +
                " name VARCHAR(50) NOT NULL," +
                " lastName VARCHAR(50) NOT NULL," +
                " age INT(3) NOT NULL )";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);

        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }


    public void dropUsersTable() throws SQLException {
        Connection connection = util.getConnection();
        String sql = "DROP TABLE IF EXISTS users";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = util.getConnection();
        String sql = "INSERT users( name, lastName, age)  VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = util.getConnection();
        String sql = "DELETE FROM users WHERE ID = ? ";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = util.getConnection();
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge((byte) resultSet.getByte(4));
                userList.add(user);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = util.getConnection();
        String sql = "TRUNCATE TABLE USERS";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
