package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.JwtToken;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    JwtToken findByTokenEquals(String token);
    JwtToken findByUser(User user);
}
