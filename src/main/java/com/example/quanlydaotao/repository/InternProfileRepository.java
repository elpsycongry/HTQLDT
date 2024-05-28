package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface InternProfileRepository extends JpaRepository<InternProfile, Long> {
//    Optional<InternProfile> findByUser(User user);
    List<InternProfile> findByIsPassEquals(boolean isPass);
    List<InternProfile> findByIsPassEqualsAndTrainingStateEquals(boolean isPass, String trainingState);
    List<InternProfile> findByTrainingStateEquals(String trainingState);
}
