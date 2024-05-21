package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.RecruitmentPlan;

public class InternSearchDTO {
    private String nameOrEmail;
    private String status;
    private RecruitmentPlan recruitmentPlan;

    public InternSearchDTO(String nameOrEmail, String status, RecruitmentPlan recruitmentPlan) {
        this.nameOrEmail = nameOrEmail;
        this.status = status;
        this.recruitmentPlan = recruitmentPlan;
    }

    public InternSearchDTO(InternSearchDTO internDTO) {
    }

    public String getNameOrEmail() {
        return nameOrEmail;
    }

    public void setNameOrEmail(String nameOrEmail) {
        this.nameOrEmail = nameOrEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RecruitmentPlan getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public void setRecruitmentPlan(RecruitmentPlan recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
    }
}
