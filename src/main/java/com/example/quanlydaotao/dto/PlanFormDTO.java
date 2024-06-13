package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.RecruitmentPlan;
import com.example.quanlydaotao.model.RecruitmentPlanDetail;
import com.example.quanlydaotao.model.RecruitmentRequest;

import java.util.List;

public class PlanFormDTO {
    private Long idUser;
    private RecruitmentPlan recruitmentPlan;
    private List<RecruitmentPlanDetail> planDetails;
    private RecruitmentRequest recruitmentRequest;

    public Long getIdUser() {
        return idUser;
    }

    public PlanFormDTO setIdUser(Long idUser) {
        this.idUser = idUser;
        return this;
    }

    public RecruitmentPlan getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public PlanFormDTO setRecruitmentPlan(RecruitmentPlan recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
        return this;
    }

    public List<RecruitmentPlanDetail> getPlanDetails() {
        return planDetails;
    }

    public PlanFormDTO setPlanDetails(List<RecruitmentPlanDetail> planDetails) {
        this.planDetails = planDetails;
        return this;
    }

    public RecruitmentRequest getRecruitmentRequest() {
        return recruitmentRequest;
    }

    public PlanFormDTO setRecruitmentRequest(RecruitmentRequest recruitmentRequest) {
        this.recruitmentRequest = recruitmentRequest;
        return this;
    }
}
