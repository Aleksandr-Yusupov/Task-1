package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        try {
            sessionFactory = getSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id serial PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age INT)")
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Таблица удалена");
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        System.out.println("Try to save user");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Пользователь с именем " + name + " добавлен");
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM users WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Пользователь с id " + id + " удален");
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM users").getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Запрос на получение данных выполнен");
        return users;
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users")
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Таблица очищена");
    }
}
