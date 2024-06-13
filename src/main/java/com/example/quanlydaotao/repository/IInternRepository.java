package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.model.RecruitmentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInternRepository extends JpaRepository<Intern,Long>, JpaSpecificationExecutor<Intern> {
    Page<Intern> findAllByOrderByIdDesc(Pageable pageable);
    Iterable<Intern> findByRecruitmentPlanId(long recruitmentPlan);
    int countByRecruitmentPlanId(long idRecruitmentPlan);
    List<Intern> findByCheckInterviewEquals(boolean checkInterview);
    List<Intern> findByFinalResultEquals(String finalResult);
    List<Intern> findByStatusEquals(String status);
}
