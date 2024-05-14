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

    public RecruitmentPlanDetail setType(String type) {
        this.type = type;
        return this;
    }

    public String getNumberOfPersonnelNeeded() {
        return numberOfPersonnelNeeded;
    }

    public RecruitmentPlanDetail setNumberOfPersonnelNeeded(String numberOfPersonnelNeeded) {
        this.numberOfPersonnelNeeded = numberOfPersonnelNeeded;
        return this;
    }

    public String getNumberOfOutputPersonnel() {
        return numberOfOutputPersonnel;
    }

    public RecruitmentPlanDetail setNumberOfOutputPersonnel(String numberOfOutputPersonnel) {
        this.numberOfOutputPersonnel = numberOfOutputPersonnel;
        return this;
    }
}
