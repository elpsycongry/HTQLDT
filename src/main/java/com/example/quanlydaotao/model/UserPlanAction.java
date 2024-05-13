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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public RecruitmentPlan getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public void setRecruitmentPlan(RecruitmentPlan recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
