package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.RecruitmentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRecruitmentPlanService {
    Page<RecruitmentPlan> showRecruitmentPlan(Pageable pageable);
    Optional<RecruitmentPlan> showRecruitmentPlanById(long id);

}
