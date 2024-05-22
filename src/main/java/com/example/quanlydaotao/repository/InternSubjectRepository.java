package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.InternSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternSubjectRepository extends JpaRepository<InternSubject, Long> {
    Optional<InternSubject> findByName(String name);
}
