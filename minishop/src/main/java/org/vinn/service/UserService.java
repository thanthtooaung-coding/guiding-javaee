package org.vinn.service;

import org.vinn.dto.UserDto;
import org.vinn.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void create(String username, String password, int userType) throws Exception;
    List<UserDto> retrieveAll() throws Exception;
    UserDto retrieveOne(Long id) throws Exception;
    Optional<User> findByUsername(String username) throws Exception;
    void edit(Long id, String username, String password) throws Exception;
    void delete(Long id) throws Exception;
}
