package com.example.quanlydaotao.model;

import jakarta.persistence.*;

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
    @JoinColumn(name = "demand_originator_id", referencedColumnName = "id")
    private UserRecruitmentAction demandOriginator;

    private String name;

    @ManyToOne
    @JoinColumn(name = "demand_confirmer_id", referencedColumnName = "id")
    private UserRecruitmentAction demandConfirmer;

    private Date dateRecruitmentEnd;

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

    public UserRecruitmentAction getDemandOriginator() {
        return demandOriginator;
    }

    public RecruitmentPlan setDemandOriginator(UserRecruitmentAction demandOriginator) {
        this.demandOriginator = demandOriginator;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecruitmentPlan setName(String name) {
        this.name = name;
        return this;
    }

    public UserRecruitmentAction getDemandConfirmer() {
        return demandConfirmer;
    }

    public RecruitmentPlan setDemandConfirmer(UserRecruitmentAction demandConfirmer) {
        this.demandConfirmer = demandConfirmer;
        return this;
    }

    public Date getDateRecruitmentEnd() {
        return dateRecruitmentEnd;
    }

    public RecruitmentPlan setDateRecruitmentEnd(Date dateRecruitmentEnd) {
        this.dateRecruitmentEnd = dateRecruitmentEnd;
        return this;
    }
}
