package com.mycrudapp.dao;
import com.mycrudapp.entity.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    List<User> listUsers();
    void deleteUser(int id);
    User getUserById(int id);
    void updateUser(User user);
    User findByUsername(String username);
}
