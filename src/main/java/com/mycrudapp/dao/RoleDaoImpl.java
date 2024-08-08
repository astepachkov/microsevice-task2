package com.mycrudapp.dao;

import com.mycrudapp.entity.Role;
import com.mycrudapp.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT role FROM Role role", Role.class).getResultList();
    }

@Override
public Set<Role> getRolesByIds(List<Long> roleIds) {
    return new HashSet<>(entityManager.createQuery("SELECT r FROM Role r WHERE r.id IN :ids", Role.class)
            .setParameter("ids", roleIds)
            .getResultList());
}

    @Override
    public Role getRoleById(long id) {
        return entityManager.find(Role.class, id) ;
    }

    @Override
    public void removeRoleFromUser(int userId, int roleId) {
        User user = entityManager.find(User.class, userId);
        Role role = entityManager.find(Role.class, roleId);
        if (user!= null && role!= null) {
            user.getRoles().remove(role);
            entityManager.merge(user);
        }
    }
}