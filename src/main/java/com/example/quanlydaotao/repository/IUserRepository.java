package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Users, Long> {
}