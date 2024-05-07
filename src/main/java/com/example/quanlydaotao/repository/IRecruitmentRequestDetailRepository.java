package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRecruitmentRequestDetailRepository extends JpaRepository<RecruitmentRequestDetail, Long> {
    Optional<RecruitmentRequestDetail> findByRecruitmentRequestId(Long id);
}
