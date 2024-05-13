package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecruitmentPlanRepository extends JpaRepository<RecruitmentPlan, Long> {
    Page<RecruitmentPlan> findAll(Pageable pageable);
}
