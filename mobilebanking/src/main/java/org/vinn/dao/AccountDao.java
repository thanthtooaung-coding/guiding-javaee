package org.vinn.dao;

import org.vinn.model.Account;
import org.vinn.model.User;

import java.util.List;
import java.util.Optional;

public interface AccountDao {
    void saveAccount(Account account);
    Optional<Account> findByAccountNumber(String accountNumber);
    void updateAccount(Account account);
    List<Account> findAccountsByUser(User user); // New method
}
