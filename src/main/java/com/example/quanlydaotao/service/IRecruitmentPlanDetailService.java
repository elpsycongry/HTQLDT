package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.RecruitmentPlanDetail;

public interface IRecruitmentPlanDetailService {
    Iterable<RecruitmentPlanDetail> findByRecruitmentPlanId(long recruitmentPlanId);
    Iterable<RecruitmentPlanDetail> findAll();
}
