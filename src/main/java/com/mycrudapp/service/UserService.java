package com.mycrudapp.service;

import com.mycrudapp.entity.Role;
import com.mycrudapp.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> listUsers();

    void deleteUser(int id);

    User getUserById(int id);

    void updateUser(User user);

    void addRoleToUser(int userId, int roleId);

    void removeRoleFromUser(int userId, int roleId);
}
