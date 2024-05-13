package com.example.quanlydaotao.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class RecruitmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "recruitment_request_id", referencedColumnName = "id")
    private RecruitmentRequest recruitmentRequest;

    @ManyToOne
    @JoinColumn(name = "user_plan_id", referencedColumnName = "id")
    private Users users;
    @Column(unique = true, nullable = false)
    private String name;
    private LocalDateTime dateCreatePlan;
    private LocalDate handoverDeadline;
    private LocalDate dateRecruitmentEnd;
    private String status;
    private String reason;
    public Long getId() {
        return id;
    }

    public RecruitmentPlan setId(Long id) {
        this.id = id;
        return this;
    }

    public RecruitmentRequest getRecruitmentRequest() {
        return recruitmentRequest;
    }

    public RecruitmentPlan setRecruitmentRequest(RecruitmentRequest recruitmentRequest) {
        this.recruitmentRequest = recruitmentRequest;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public RecruitmentPlan setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getDateCreatePlan() {
        return dateCreatePlan;
    }

    public void setDateCreatePlan(LocalDateTime dateCreatePlan) {
        this.dateCreatePlan = dateCreatePlan;
    }

    public LocalDate getDateRecruitmentEnd() {
        return dateRecruitmentEnd;
    }

    public void setDateRecruitmentEnd(LocalDate dateRecruitmentEnd) {
        this.dateRecruitmentEnd = dateRecruitmentEnd;
    }

    public LocalDate getHandoverDeadline() {
        return handoverDeadline;
    }

    public void setHandoverDeadline(LocalDate handoverDeadline) {
        this.handoverDeadline = handoverDeadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

