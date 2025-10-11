package org.vinn.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.UserDao;
import org.vinn.model.User;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public void save(User User) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(User);
            transaction.commit();
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("from User", User.class)
                    .list();
        }
    }

    @Override
    public User findById(Long id) throws Exception {
        System.out.println("Pass 4");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Pass 5");
            System.out.println("User Id: " + id);
            return session
                    .get(User.class, id);
        }
    }

    @Override
    public void update(User User) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(User);
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User User = session.get(User.class, id);
            session.delete(User);
            transaction.commit();
        }
    }
}
