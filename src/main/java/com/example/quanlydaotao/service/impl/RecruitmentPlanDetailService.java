package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.RecruitmentPlanDetail;
import com.example.quanlydaotao.repository.IRecruitmentPlanDetailRepository;
import com.example.quanlydaotao.service.IRecruitmentPlanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitmentPlanDetailService implements IRecruitmentPlanDetailService {
    @Autowired
    private IRecruitmentPlanDetailRepository recruitmentPlanDetailRepository;
    @Override
    public Iterable<RecruitmentPlanDetail> findByRecruitmentPlanId(long recruitmentPlanId) {
        return recruitmentPlanDetailRepository.findByRecruitmentPlanId(recruitmentPlanId);
    }

    @Override
    public Iterable<RecruitmentPlanDetail> findAll() {
        return recruitmentPlanDetailRepository.findAll();
    }
    public void save(RecruitmentPlanDetail recruitmentPlanDetail) {
        recruitmentPlanDetailRepository.save(recruitmentPlanDetail);
    }
}
