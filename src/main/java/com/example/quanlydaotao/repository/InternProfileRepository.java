package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternProfileRepository extends JpaRepository<InternProfile, Long> {
    Optional<InternProfile> findByUser(User user);
}
