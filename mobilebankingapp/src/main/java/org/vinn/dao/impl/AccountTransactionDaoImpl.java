package org.vinn.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.AccountTransactionDao;
import org.vinn.model.Account;
import org.vinn.model.AccountTransaction;

import java.util.List;

public class AccountTransactionDaoImpl implements AccountTransactionDao {
    @Override
    public void saveTransaction(AccountTransaction accountTransaction) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(accountTransaction);
            transaction.commit();
        }
    }

    @Override
    public List<AccountTransaction> findTransactionsByAccount(Account account) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery(
                            "SELECT t FROM AccountTransaction t "+
                                    "join fetch t.sourceAccount "+
                                    "join fetch t.targetAccount "+
                                    "WHERE t.sourceAccount = :account OR t.targetAccount = :account", AccountTransaction.class
                    ).setParameter("account", account)
                    .list();
        }
    }
}
