package org.vinn.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.AccountDao;
import org.vinn.model.Account;
import org.vinn.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    @Override
    public void saveAccount(Account account) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Account account = session.createQuery("from Account where accountNumber = :accountNumber", Account.class)
                                     .setParameter("accountNumber", accountNumber)
                                     .uniqueResult();
            return Optional.ofNullable(account);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void updateAccount(Account account) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> findAccountsByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Account where user = :user", Account.class)
                    .setParameter("user", user)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
