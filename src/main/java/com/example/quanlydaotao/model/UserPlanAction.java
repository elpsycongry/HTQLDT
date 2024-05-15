package com.example.quanlydaotao.model;

import jakarta.persistence.*;

@Entity
public class UserPlanAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "recruitment_plan_id", referencedColumnName = "id")
    private RecruitmentPlan recruitmentPlan;
    private String action;

    public UserPlanAction() {
    }

    public UserPlanAction(Users user, RecruitmentPlan recruitmentPlan) {
        this.user = user;
        this.recruitmentPlan = recruitmentPlan;
    }

    public Long getId() {
        return id;
    }

    public UserPlanAction setId(Long id) {
        this.id = id;
        return this;
    }

    public Users getUser() {
        return user;
    }

    public UserPlanAction setUser(Users user) {
        this.user = user;
        return this;
    }

    public RecruitmentPlan getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public UserPlanAction setRecruitmentPlan(RecruitmentPlan recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
        return this;
    }

    public String getAction() {
        return action;
    }

    public UserPlanAction setAction(String action) {
        this.action = action;
        return this;
    }
}
