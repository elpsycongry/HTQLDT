package com.example.quanlydaotao.model;

import com.example.quanlydaotao.dto.UserAction;
import jakarta.persistence.*;

@Entity
public class UserRecruitmentAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "recruitment_request_id", referencedColumnName = "id")
    private RecruitmentRequest recruitmentRequest;

    private String action;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Users getUser() {
        return user;
    }

    public UserRecruitmentAction setUser(Users user) {
        this.user = user;
        return this;
    }


    public RecruitmentRequest getRecruitmentRequest() {
        return recruitmentRequest;
    }

    public UserRecruitmentAction setRecruitmentRequest(RecruitmentRequest recruitmentRequest) {
        this.recruitmentRequest = recruitmentRequest;
        return this;
    }

    public String getAction() {
        return action;
    }

    public UserRecruitmentAction setAction(String action) {
        this.action = action;
        return this;
    }
}
