package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.model.RecruitmentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IInternRepository extends JpaRepository<Intern,Long>, JpaSpecificationExecutor<Intern> {
    Page<Intern> findAllByOrderByIdDesc(Pageable pageable);
public interface IInternRepository extends JpaRepository<Intern,Long> {
    Page<Intern> findAllByOrderByIdDesc(Pageable pageable);
    Iterable<Intern> findByRecruitmentPlan(RecruitmentPlan recruitmentPlan);
    int countByRecruitmentPlanId(long idRecruitmentPlan);
}
