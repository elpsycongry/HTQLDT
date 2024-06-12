package com.example.quanlydaotao.model;

import java.util.List;

public class SendEmail {
    private List<User> users;
    private List<RecruitmentRequest> recruitmentRequests;
    private List<RecruitmentPlanDetail> recruitmentPlanDetails;

    public SendEmail(List<User> users, List<RecruitmentRequest> recruitmentRequests, List<RecruitmentPlanDetail> recruitmentPlanDetails) {
        this.users = users;
        this.recruitmentRequests = recruitmentRequests;
        this.recruitmentPlanDetails = recruitmentPlanDetails;
    }

    public SendEmail() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<RecruitmentRequest> getRecruitmentRequests() {
        return recruitmentRequests;
    }

    public void setRecruitmentRequests(List<RecruitmentRequest> recruitmentRequests) {
        this.recruitmentRequests = recruitmentRequests;
    }

    public List<RecruitmentPlanDetail> getRecruitmentPlanDetails() {
        return recruitmentPlanDetails;
    }

    public void setRecruitmentPlanDetails(List<RecruitmentPlanDetail> recruitmentPlanDetails) {
        this.recruitmentPlanDetails = recruitmentPlanDetails;
    }
}
