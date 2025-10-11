package org.vinn.service.impl;

import org.hibernate.Session;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.UserDao;
import org.vinn.dao.impl.UserDaoImpl;
import org.vinn.dto.UserDto;
import org.vinn.mapper.UserMapper;
import org.vinn.model.User;
import org.vinn.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public void create(String username, String password) throws Exception {
        userDao.save(
                new User().initialize(username, password)
        );
    }

    @Override
    public List<UserDto> retrieveAll() throws Exception {
        List<User> users = userDao.findAll();
        return users.stream().map(UserMapper::toDto).toList();
    }

    @Override
    public UserDto retrieveOne(Long id) throws Exception {
        User user = userDao.findById(id);
        return UserMapper.toDto(user);
    }

    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            User user = session.
                    createQuery("from User where username = :username", User.class)
                    .setParameter("username", username).uniqueResult();
            return Optional.ofNullable(user);
        }
    }

    @Override
    public void edit(Long id, String username, String password) throws Exception {
        User user = userDao.findById(id);
        user.setUsername(username);
        user.setPassword(password);
        
        userDao.update(
                user
        );
    }

    @Override
    public void delete(Long id) throws Exception {
        userDao.delete(id);
    }

}
