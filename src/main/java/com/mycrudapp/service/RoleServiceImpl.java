package com.mycrudapp.service;

import com.mycrudapp.dao.RoleDao;
import com.mycrudapp.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }


    @Override
    public Set<Role> getRolesByIds(List<Long> roleIds) {
        return roleDao.getRolesByIds(roleIds);
    }
}
