package org.vinn.dao;

import org.vinn.model.Account;
import org.vinn.model.User;

import java.util.List;
import java.util.Optional;

public interface AccountDao {
    void saveAccount(Account account) throws Exception;
    Optional<Account> findByAccountNumber(String accountNumber) throws Exception;
    void updateAccount(Account account) throws Exception;
    List<Account> findAccountsByUser(User user) throws Exception;
}
