package com.example.quanlydaotao.model;

import jakarta.persistence.*;

import java.time.LocalDate;

public class InternProfileDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String trainingState;
    private Boolean isPass;

    public InternProfileDTO(){}

    public InternProfileDTO(Long id, LocalDate startDate, LocalDate endDate, String trainingState, Boolean isPass) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.trainingState = trainingState;
        this.isPass = isPass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTrainingState() {
        return trainingState;
    }

    public void setTrainingState(String trainingState) {
        this.trainingState = trainingState;
    }

    public Boolean getPass() {
        return isPass;
    }

    public void setPass(Boolean pass) {
        isPass = pass;
    }
}
