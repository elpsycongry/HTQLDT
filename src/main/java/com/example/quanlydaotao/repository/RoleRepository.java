package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String roleName);
}
