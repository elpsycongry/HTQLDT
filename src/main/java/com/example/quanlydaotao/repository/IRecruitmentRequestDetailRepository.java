package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface IRecruitmentRequestDetailRepository extends JpaRepository<RecruitmentRequestDetail, Long> {
    Iterable<RecruitmentRequestDetail> findByRecruitmentRequestId(Long id);
    void deleteAllByRecruitmentRequestId(long id);

}
