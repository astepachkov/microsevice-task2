package com.mycrudapp.dao;

import com.mycrudapp.entity.Role;
import com.mycrudapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user!= null) {
            Query query = entityManager.createNativeQuery("DELETE FROM user_roles WHERE user_id = ?");
            query.setParameter(1, id);
            query.executeUpdate();
            entityManager.remove(user);
        }

    }
    @Override
    public void addUser(User user) {
            entityManager.persist(user);
            for (Role role : user.getRoles()) {
                entityManager.merge(role);
            }
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> typedQuery = entityManager.createQuery("from User", User.class);
        return typedQuery.getResultList();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        User existingUser = entityManager.find(User.class, user.getId());
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setRoles(user.getRoles());
            existingUser.setUsername(user.getUsername());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }
            entityManager.merge(existingUser);
        }
    }

    @Override
    public List<User> getUserByPhone(String phone) {
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.phoneNumber = :phone", User.class)
                .setParameter("phone", phone)
                .getResultList();

        if (users.isEmpty()) {
            System.out.println("Пользователи с номером " + phone + " не найдены");
        }

        return users;
    }

    @Override
    public List<User> getUserBySurname(String surname) {
        TypedQuery<User> typedQuery = entityManager.createQuery("from User user where user.surname = :surname", User.class);
        typedQuery.setParameter("surname", surname);
        List<User> users = typedQuery.getResultList();
        if (users.isEmpty()) {
            System.out.println("Пользователи с фамилией " + surname + " не найдены");
            return Collections.emptyList();
        } else return users;
    }

    @Override
    public List<Role> getUserRoles(int userId) {
        User user = entityManager.find(User.class, userId);
        return new ArrayList<>(user.getRoles());
    }

    @Override
    public void addRoleToUser(int userId, Role role) {
        User user = entityManager.find(User.class, userId);
        user.getRoles().add(role);
        entityManager.merge(user);
    }

    @Override
    public void removeRoleFromUser(int userId, Role role) {
        User user = entityManager.find(User.class, userId);
        user.getRoles().remove(role);
        entityManager.merge(user);
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

}
