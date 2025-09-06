package org.vinn.dao;

import org.vinn.model.Account;
import org.vinn.model.Transaction;
import java.util.List;

public interface TransactionDao {
    void saveTransaction(Transaction transaction);
    List<Transaction> findTransactionsByAccount(Account account) throws Exception;
}
