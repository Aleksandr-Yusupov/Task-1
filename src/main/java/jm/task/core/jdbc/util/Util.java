package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:postgresql://localhost:5432/task1";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static SessionFactory getSessionFactory() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Properties properties = new Properties();
        properties.load(classLoader.getResourceAsStream("hibernate.properties"));

        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addProperties(properties)
                .buildSessionFactory();
        return sessionFactory;
    }

}
