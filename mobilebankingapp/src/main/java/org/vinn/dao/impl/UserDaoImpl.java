package org.vinn.dao.impl;

import org.hibernate.*;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.UserDao;
import org.vinn.model.User;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    @Override
    public void saveUser(User user) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User").list();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session
                            .createQuery("from User where username = :username", User.class)
                            .setParameter("username", username).uniqueResult();
            return Optional.ofNullable(user);
        }
    }
}
