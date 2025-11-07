package org.vinn.dao;

import org.vinn.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void saveUser(User user) throws Exception;
    List<User> getAllUsers() throws Exception;
    Optional<User> findByUsername(String username) throws Exception;
}
