package com.mycrudapp.dao;

import com.mycrudapp.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    List<Role> getAllRoles();


    Set<Role> getRolesByIds(List<Long> roleIds);
}