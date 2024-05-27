package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
}
