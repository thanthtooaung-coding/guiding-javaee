package org.vinn.dao.impl;

import org.hibernate.Session;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.TransactionDao;
import org.vinn.model.Account;
import org.vinn.model.Transaction;

import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    @Override
    public void saveTransaction(Transaction transaction) {
        org.hibernate.Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(transaction);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> findTransactionsByAccount(Account account) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select t from Transaction t " +
                                    "join fetch t.sourceAccount " +
                                    "join fetch t.targetAccount " +
                                    "where t.sourceAccount = :account or t.targetAccount = :account " +
                                    "order by t.transactionDate desc", Transaction.class)
                    .setParameter("account", account)
                    .list();
        }
    }
}
