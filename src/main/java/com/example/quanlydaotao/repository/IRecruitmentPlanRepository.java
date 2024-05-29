package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecruitmentPlanRepository extends JpaRepository<RecruitmentPlan, Long>, JpaSpecificationExecutor<RecruitmentPlan> {
    List<RecruitmentPlan> findAllByOrderByDateCreatePlanDesc(Sort sort);
    List<RecruitmentPlan> findByStatusEquals(String status);
}
