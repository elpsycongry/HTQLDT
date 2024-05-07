package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long> {
}
