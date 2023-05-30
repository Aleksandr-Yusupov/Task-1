package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id serial PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age INT)");
            System.out.println("Таблица создана");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Таблица удалена");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("Пользователь с именем " + name + " добавлен");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
        String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Пользователь с id " + id + " удалён");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
            System.out.println("Запрос на поиск всех пользователей выполнен");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            int rows = statement.executeUpdate("DELETE FROM users");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println("Таблица очищена");
    }
}

