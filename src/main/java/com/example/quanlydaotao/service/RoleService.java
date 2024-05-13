package com.example.quanlydaotao.service;


import com.example.quanlydaotao.model.Role;

import java.util.Optional;

public interface RoleService {
    Iterable<Role> findAll();
    Optional<Role> findById(Long id);

    void save(Role role);

    Role findByName(String name);
}