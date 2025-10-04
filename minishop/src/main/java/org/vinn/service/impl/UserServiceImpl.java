package org.vinn.service.impl;

import org.vinn.dao.UserDao;
import org.vinn.dao.impl.UserDaoImpl;
import org.vinn.dto.UserDto;
import org.vinn.mapper.UserMapper;
import org.vinn.model.User;
import org.vinn.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao UserDao;

    public UserServiceImpl() {
        this.UserDao = new UserDaoImpl();
    }

    @Override
    public void create(String name) throws Exception {
        UserDao.save(
                new User().initialize(name)
        );
    }

    @Override
    public List<UserDto> retrieveAll() throws Exception {
        List<User> users = UserDao.findAll();
        return users.stream().map(UserMapper::toDto).toList();
    }

    @Override
    public UserDto retrieveOne(Long id) throws Exception {
        User User = UserDao.findById(id);
        return UserMapper.toDto(User);
    }

    @Override
    public void edit(Long id, String name) throws Exception {
        User User = UserDao.findById(id);
        User.setName(name);
        UserDao.update(
                User
        );
    }

    @Override
    public void delete(Long id) throws Exception {
        UserDao.delete(id);
    }

}
