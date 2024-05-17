package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.InternProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternProfileRepository extends JpaRepository<InternProfile, Long> {
    List<InternProfile> findByIsPassEquals(boolean isPass);
    List<InternProfile> findByTrainingStateEquals(String trainingState);
}
