package com.mycrudapp.service;

import com.mycrudapp.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Role findByName(String name);
    List<Role> getAllRoles();
    void saveRole(Role role);
    void deleteRole(Role role);

    Set<Role> getRolesByIds(List<Long> roleIds);
}