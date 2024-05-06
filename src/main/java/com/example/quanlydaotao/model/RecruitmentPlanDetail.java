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
}
