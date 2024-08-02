package com.mycrudapp.service;

import com.mycrudapp.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    Set<Role> getRolesByIds(List<Long> roleIds);
}