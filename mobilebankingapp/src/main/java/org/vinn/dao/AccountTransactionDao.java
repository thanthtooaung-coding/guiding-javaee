package org.vinn.dao;

import org.vinn.model.Account;
import org.vinn.model.AccountTransaction;

import java.util.List;

public interface AccountTransactionDao {
    void saveTransaction(AccountTransaction accountTransaction) throws Exception;

    List<AccountTransaction> findTransactionsByAccount(Account account) throws Exception;
}
