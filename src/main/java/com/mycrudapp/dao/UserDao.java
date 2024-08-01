package com.mycrudapp.dao;

import com.mycrudapp.entity.Role;
import com.mycrudapp.entity.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    List<User> listUsers();
    List<User> getUserByPhone(String phone);
    List<User> getUserBySurname(String surname);
    void deleteUser(int id);
    User getUserById(int id);
    void updateUser(User user);
    List<Role> getUserRoles(int userId);
    void addRoleToUser(int userId, Role role);
    void removeRoleFromUser(int userId, Role role);
    User findByUsername(String username);
}
