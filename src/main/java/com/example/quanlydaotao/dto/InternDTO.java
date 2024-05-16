package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.dto.InternSubjectDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class InternDTO {
    private Long id;
    private String userName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long numberDate;
    private String trainingState;
    private Boolean isPass;
    private double finalScore;
    private String scoreInTeam;
    private List<InternSubjectDTO> internScoreDTOList;

    public InternDTO(){}

    public InternDTO(Long id, String userName, LocalDate startDate, LocalDate endDate, String trainingState, Boolean isPass, double finalScore, String scoreInTeam, List<InternSubjectDTO> internScoreDTOList) {
        this.id = id;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberDate = ChronoUnit.DAYS.between(startDate, endDate);
        this.trainingState = trainingState;
        this.isPass = isPass;
        this.finalScore = finalScore;
        this.scoreInTeam = scoreInTeam;
        this.internScoreDTOList = internScoreDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<InternSubjectDTO> getInternScoreDTOList() {
        return internScoreDTOList;
    }

    public void setInternScoreDTOList(List<InternSubjectDTO> internScoreDTOList) {
        this.internScoreDTOList = internScoreDTOList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Long getNumberDate() {
        return numberDate;
    }

    public void setNumberDate(Long numberDate) {
        this.numberDate = numberDate;
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

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public String getScoreInTeam() {
        return scoreInTeam;
    }

    public void setScoreInTeam(String scoreInTeam) {
        this.scoreInTeam = scoreInTeam;
    }

}

