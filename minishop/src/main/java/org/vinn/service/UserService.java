package org.vinn.service;

import org.vinn.dto.UserDto;

import java.util.List;

public interface UserService {
    void create(String name) throws Exception;
    List<UserDto> retrieveAll() throws Exception;
    UserDto retrieveOne(Long id) throws Exception;
    void edit(Long id, String name) throws Exception;
    void delete(Long id) throws Exception;
}
