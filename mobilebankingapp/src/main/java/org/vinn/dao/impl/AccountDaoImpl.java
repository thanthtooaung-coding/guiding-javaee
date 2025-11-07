package org.vinn.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.AccountDao;
import org.vinn.model.Account;
import org.vinn.model.User;

import java.util.List;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {
    @Override
    public void saveAccount(Account account) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        }
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Account account = session
                    .createQuery(
                            "select a from Account a " +
                                    "join fetch a.user " +
                                    "where a.accountNumber = :accountNumber",
                            Account.class
                    )
                    .setParameter("accountNumber", accountNumber)
                    .uniqueResult();
            return Optional.ofNullable(account);
        }
    }

    @Override
    public void updateAccount(Account account) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(account);
            transaction.commit();
        }
    }

    @Override
    public List<Account> findAccountsByUser(User user) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("from Account where user = :user", Account.class)
                    .setParameter("user", user)
                    .list();
        }
    }
}
