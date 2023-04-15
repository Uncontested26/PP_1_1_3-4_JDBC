package jm.task.core.jdbc;

import com.mysql.jdbc.Driver;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();

         userService.createUsersTable();

        userService.saveUser("Илья", "Кухарюк", (byte) 33);
        userService.saveUser("Тарас", "Кухарюк", (byte) 104);
        userService.saveUser("Сергей", "Соколов", (byte) 36);
        userService.saveUser("Иван", "Иванов", (byte) 44);

        userService.removeUserById(3);

         userService.getAllUsers();

         userService.cleanUsersTable();

         userService.dropUsersTable();


    }
}
