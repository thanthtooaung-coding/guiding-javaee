package org.vinn.dao;

import org.vinn.model.User;

import java.util.List;

public interface UserDao {
    void save(User User)throws Exception;
    List<User> findAll()throws Exception;
    User findById(Long id) throws Exception;
    void update(User User) throws Exception;
    void delete(Long id) throws Exception;
}
