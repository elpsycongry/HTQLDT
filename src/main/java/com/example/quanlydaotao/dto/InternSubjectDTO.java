package com.example.quanlydaotao.dto;

public class InternSubjectDTO {
    private Long id;
    private String nameSubject;
    private String theoryScore;
    private String practiceScore;
    private String attitudeScore;
    private String totalScore;
    public InternSubjectDTO() {}

    public InternSubjectDTO(Long id, String nameSubject) {
        this.id = id;
        this.nameSubject = nameSubject;
        this.theoryScore = "NA";
        this.practiceScore = "NA";
        this.attitudeScore = "NA";
        this.totalScore = "NA";
    }


    public InternSubjectDTO(Long id, String nameSubject, String theoryScore, String practiceScore, String attitudeScore, String totalScore) {
        this.id = id;
        this.nameSubject = nameSubject;
        this.theoryScore = theoryScore;
        this.practiceScore = practiceScore;
        this.attitudeScore = attitudeScore;
        this.totalScore = totalScore;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getTheoryScore() {
        return theoryScore;
    }

    public void setTheoryScore(String theoryScore) {
        this.theoryScore = theoryScore;
    }

    public String getPracticeScore() {
        return practiceScore;
    }

    public void setPracticeScore(String practiceScore) {
        this.practiceScore = practiceScore;
    }

    public String getAttitudeScore() {
        return attitudeScore;
    }

    public void setAttitudeScore(String attitudeScore) {
        this.attitudeScore = attitudeScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }
}
