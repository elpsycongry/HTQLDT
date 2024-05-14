package com.example.quanlydaotao.model;

import jakarta.persistence.*;

@Entity
public class RecruitmentPlanDetail {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "recruitment_plan_id", referencedColumnName = "id")
    private RecruitmentPlan recruitmentPlan;
    public Long getId() {
        return id;
    }
    private String type;
    private String numberOfPersonnelNeeded;
    private String numberOfOutputPersonnel;
    public RecruitmentPlanDetail setId(Long id) {
        this.id = id;
        return this;
    }

    public RecruitmentPlan getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public RecruitmentPlanDetail setRecruitmentPlan(RecruitmentPlan recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumberOfPersonnelNeeded() {
        return numberOfPersonnelNeeded;
    }

    public void setNumberOfPersonnelNeeded(String numberOfPersonnelNeeded) {
        this.numberOfPersonnelNeeded = numberOfPersonnelNeeded;
    }

    public String getNumberOfOutputPersonnel() {
        return numberOfOutputPersonnel;
    }

    public void setNumberOfOutputPersonnel(String numberOfOutputPersonnel) {
        this.numberOfOutputPersonnel = numberOfOutputPersonnel;
    }
}
