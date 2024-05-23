package com.example.quanlydaotao.dto;


import com.example.quanlydaotao.dto.InternSubjectDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class InternDTO {
    private Long id;
    private String userName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long numberDate;
    private String trainingState;
    private Boolean isPass;
    private String finalScore;
    private String scoreInTeam;
    private List<InternSubjectDTO> internSubjectDTOList;

    public InternDTO(){}

    public InternDTO(Long id, String userName, LocalDate startDate, LocalDate endDate, String trainingState, String finalScore, String scoreInTeam, List<InternSubjectDTO> internSubjectDTOList) {
        this.id = id;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberDate = countDay(startDate, endDate);
        this.trainingState = trainingState;
        this.isPass = setIsPass(finalScore, scoreInTeam);
        this.finalScore = finalScore;
        this.scoreInTeam = scoreInTeam;
        this.internSubjectDTOList = internSubjectDTOList;
    }
    
    private Long countDay(LocalDate startDate, LocalDate endDate) {
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        long numberDate = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                numberDate--;
            }
        }
        return numberDate;
    }

    private Boolean setIsPass(String finalScoreIntern, String scoreInTeamIntern) {
        if (finalScoreIntern == null || scoreInTeamIntern == null || finalScoreIntern.isEmpty() || scoreInTeamIntern.isEmpty()) {
            return null;
        }
        double finalScore = Double.parseDouble(finalScoreIntern);
        double scoreInTeam = Double.parseDouble(scoreInTeamIntern);

        if (finalScore >= 7 && scoreInTeam >= 7) {
            return true;
        } else if (finalScore < 7) {
            return false;
        } else {
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<InternSubjectDTO> getInternSubjectDTOList() {
        return internSubjectDTOList;
    }

    public void setInternSubjectDTOList(List<InternSubjectDTO> internSubjectDTOList) {
        this.internSubjectDTOList = internSubjectDTOList;
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

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    public String getScoreInTeam() {
        return scoreInTeam;
    }

    public void setScoreInTeam(String scoreInTeam) {
        this.scoreInTeam = scoreInTeam;
    }

}

