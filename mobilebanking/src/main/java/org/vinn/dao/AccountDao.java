package org.vinn.dao;

import org.vinn.model.Account;
import java.util.Optional;

public interface AccountDao {
    void saveAccount(Account account);
    Optional<Account> findByAccountNumber(String accountNumber);
    void updateAccount(Account account);
}
