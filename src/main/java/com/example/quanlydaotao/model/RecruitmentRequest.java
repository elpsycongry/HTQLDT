package com.example.quanlydaotao.model;

import com.example.quanlydaotao.dto.UserAction;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class RecruitmentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String name;


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

    public LocalDate getDateStart() {
        return dateStart;
    }

    public RecruitmentRequest setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
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
