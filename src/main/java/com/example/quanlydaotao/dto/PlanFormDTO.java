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

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public RecruitmentPlan getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public void setRecruitmentPlan(RecruitmentPlan recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
    }

    public List<RecruitmentPlanDetail> getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(List<RecruitmentPlanDetail> planDetails) {
        this.planDetails = planDetails;
    }

    public RecruitmentRequest getRecruitmentRequest() {
        return recruitmentRequest;
    }

    public void setRecruitmentRequest(RecruitmentRequest recruitmentRequest) {
        this.recruitmentRequest = recruitmentRequest;
    }
}
