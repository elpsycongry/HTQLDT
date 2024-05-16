package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternScoreRepository extends JpaRepository<InternScore, Long> {
    List<InternScore> findAllByUser(User user);
}
