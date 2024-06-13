package com.example.quanlydaotao.dto;

public class RecruitmentPlanDashBoardDTO {
    private int totalRecruitmentPlan;
    private int awaitingApproval;
    private int approved;
    private int Accomplished;
    public RecruitmentPlanDashBoardDTO() {}
    public RecruitmentPlanDashBoardDTO(int totalRecruitmentPlan, int awaitingApproval, int approved, int Accomplished) {
        this.totalRecruitmentPlan = totalRecruitmentPlan;
        this.awaitingApproval = awaitingApproval;
        this.approved = approved;
        this.Accomplished = Accomplished;
    }

    public int getTotalRecruitmentPlan() {
        return totalRecruitmentPlan;
    }

    public void setTotalRecruitmentPlan(int totalRecruitmentPlan) {
        this.totalRecruitmentPlan = totalRecruitmentPlan;
    }

    public int getAwaitingApproval() {
        return awaitingApproval;
    }

    public void setAwaitingApproval(int awaitingApproval) {
        this.awaitingApproval = awaitingApproval;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getAccomplished() {
        return Accomplished;
    }

    public void setAccomplished(int accomplished) {
        Accomplished = accomplished;
    }
}
