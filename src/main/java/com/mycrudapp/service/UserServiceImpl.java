package com.mycrudapp.service;

import com.mycrudapp.dao.RoleDao;
import com.mycrudapp.dao.UserDao;
import com.mycrudapp.entity.User;

import com.mycrudapp.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final RoleDao roleDao;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        Set<Role>attachedRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role attachedRole = roleDao.getRoleById((int)role.getId());
            attachedRoles.add(attachedRole);
        }
        user.setRoles(attachedRoles);
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
        User existingUser = userDao.getUserById(user.getId());
        if (existingUser!= null) {
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                user.setPassword(existingUser.getPassword());
            }
            userDao.updateUser(user);
        }
    }

    @Override
    @Transactional
    public void addRoleToUser(int userId, int roleId) {
        User user = userDao.getUserById(userId);
        Role role = roleDao.getRoleById(roleId);
        if (user != null && role!= null) {
            user.getRoles().add(role);
            userDao.updateUser(user);
        }
    }

    @Override
    @Transactional
    public void removeRoleFromUser(int userId, int roleId) {
        User user = userDao.getUserById(userId);
        Role role = roleDao.getRoleById(roleId);
        if (user!= null && role!= null) {
            user.getRoles().remove(role);
            userDao.updateUser(user);
        }

    }

}
