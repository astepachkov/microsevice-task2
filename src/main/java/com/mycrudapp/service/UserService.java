package com.mycrudapp.service;

import com.mycrudapp.entity.Role;
import com.mycrudapp.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> listUsers();
    List<User> getUserByPhone(String phone);
    List<User> getUserBySurname(String surname);
    void deleteUser(int id);
    User getUserById(int id);
    void updateUser(User user);

    @Transactional
    List<Role> getUserRoles(int userId);

    @Transactional
    void addRoleToUser(int userId, Role role);

    @Transactional
    void removeRoleFromUser(int userId, Role role);
}
