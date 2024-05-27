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

    public Users getUsers() {
        return users;
    }

    public RecruitmentPlan setUsers(Users users) {
        this.users = users;
        return this;
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

    public RecruitmentPlan setDateCreatePlan(LocalDateTime dateCreatePlan) {
        this.dateCreatePlan = dateCreatePlan;
        return this;
    }

    public LocalDate getHandoverDeadline() {
        return handoverDeadline;
    }

    public RecruitmentPlan setHandoverDeadline(LocalDate handoverDeadline) {
        this.handoverDeadline = handoverDeadline;
        return this;
    }

    public LocalDate getDateRecruitmentEnd() {
        return dateRecruitmentEnd;
    }

    public RecruitmentPlan setDateRecruitmentEnd(LocalDate dateRecruitmentEnd) {
        this.dateRecruitmentEnd = dateRecruitmentEnd;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RecruitmentPlan setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RecruitmentPlan setReason(String reason) {
        this.reason = reason;
        return this;
    }
}

