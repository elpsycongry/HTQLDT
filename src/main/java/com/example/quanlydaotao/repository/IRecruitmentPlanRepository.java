package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecruitmentPlanRepository extends JpaRepository<RecruitmentPlan, Long> , JpaSpecificationExecutor<RecruitmentPlan> {
    Page<RecruitmentPlan> findAll(Pageable pageable);
}
