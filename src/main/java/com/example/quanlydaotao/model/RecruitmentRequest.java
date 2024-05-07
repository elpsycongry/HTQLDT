package com.example.quanlydaotao.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class RecruitmentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "demand_originator_id", referencedColumnName = "id")
    private UserRecruitmentAction demandOriginator;

    private LocalDate dateStart;
    private boolean active;
    private LocalDate dateEnd;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_confirmer_id", referencedColumnName = "id")
    private UserRecruitmentAction demandConfirmer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_originator_id", referencedColumnName = "id")
    private UserRecruitmentAction planOriginator;

    private String reason;
    private String division;
    private String status;

    public Long getId() {
        return id;
    }

    public RecruitmentRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRecruitmentAction getDemandOriginator() {
        return demandOriginator;
    }

    public RecruitmentRequest setDemandOriginator(UserRecruitmentAction demandOriginator) {
        this.demandOriginator = demandOriginator;
        return this;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public RecruitmentRequest setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public RecruitmentRequest setActive(boolean active) {
        this.active = active;
        return this;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public RecruitmentRequest setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecruitmentRequest setName(String name) {
        this.name = name;
        return this;
    }

    public UserRecruitmentAction getDemandConfirmer() {
        return demandConfirmer;
    }

    public RecruitmentRequest setDemandConfirmer(UserRecruitmentAction demandConfirmer) {
        this.demandConfirmer = demandConfirmer;
        return this;
    }

    public UserRecruitmentAction getPlanOriginator() {
        return planOriginator;
    }

    public RecruitmentRequest setPlanOriginator(UserRecruitmentAction planOriginator) {
        this.planOriginator = planOriginator;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RecruitmentRequest setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getDivision() {
        return division;
    }

    public RecruitmentRequest setDivision(String division) {
        this.division = division;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RecruitmentRequest setStatus(String status) {
        this.status = status;
        return this;
    }
}
