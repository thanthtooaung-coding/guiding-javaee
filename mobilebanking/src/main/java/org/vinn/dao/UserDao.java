package org.vinn.dao;

import org.vinn.model.User;
import java.util.Optional;

public interface UserDao {
    void saveUser(User user);
    Optional<User> findByUsername(String username);
    void updateUser(User user); // New method
}
