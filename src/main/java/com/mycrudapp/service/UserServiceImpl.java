package com.mycrudapp.service;

import com.mycrudapp.dao.RoleDao;
import com.mycrudapp.dao.UserDao;
import com.mycrudapp.entity.Role;
import com.mycrudapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getPassword()!= null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUserBySurname(String surname) {
        return userDao.getUserBySurname(surname);
    }

    @Override
    @Transactional
    public List<Role> getUserRoles(int userId) {
        return userDao.getUserRoles(userId);
    }

    @Override
    @Transactional
    public void addRoleToUser(int userId, Role role) {
        userDao.addRoleToUser(userId, role);
    }

    @Override
    @Transactional
    public void removeRoleFromUser(int userId, Role role) {
        userDao.removeRoleFromUser(userId, role);
    }

}
