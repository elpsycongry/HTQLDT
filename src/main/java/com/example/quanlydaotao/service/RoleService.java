package com.example.quanlydaotao.service;


import com.example.quanlydaotao.model.Role;

public interface RoleService {
    Iterable<Role> findAll();

    void save(Role role);

    Role findByName(String name);
}