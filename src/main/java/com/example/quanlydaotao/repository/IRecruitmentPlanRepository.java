package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRecruitmentPlanRepository extends JpaRepository<RecruitmentPlan, Long>, JpaSpecificationExecutor<RecruitmentPlan> {
    Optional<RecruitmentPlan> findByRecruitmentRequestId(long idRecruitmentRequest);
    List<RecruitmentPlan> findAllByOrderByDateCreatePlanDesc(Sort sort);
    List<RecruitmentPlan> findByStatusEquals(String status);
    List<RecruitmentPlan> findByRecruitmentRequestIdIn(List<Long> idRequests);
}
