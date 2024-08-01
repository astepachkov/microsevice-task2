package com.mycrudapp.dao;

import com.mycrudapp.entity.Role;
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
    public Role findByName(String name) {
        return entityManager.createQuery("SELECT role FROM Role role WHERE role.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT role FROM Role role", Role.class).getResultList();
    }

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void deleteRole(Role role) {
        entityManager.remove(entityManager.contains(role) ? role : entityManager.merge(role));
    }

@Override
public Set<Role> getRolesByIds(List<Long> roleIds) {
    return new HashSet<>(entityManager.createQuery("SELECT r FROM Role r WHERE r.id IN :ids", Role.class)
            .setParameter("ids", roleIds)
            .getResultList());
}
}