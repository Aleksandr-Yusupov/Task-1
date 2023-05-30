package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм
        UserService userServices = new UserServiceImpl();
        userServices.dropUsersTable();
        userServices.createUsersTable();
        userServices.saveUser("Джонни", "Депп", (byte) 26);
        userServices.saveUser("Майкл", "Джексон", (byte) 21);
        userServices.saveUser("Кира", "Найтли", (byte) 21);
        userServices.saveUser("Майк", "Тайсон", (byte) 26);
        userServices.removeUserById(4);
        List<User> allUsers = userServices.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user.toString());
        }
        userServices.cleanUsersTable();
    }
}
