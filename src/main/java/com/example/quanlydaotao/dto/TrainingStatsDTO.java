package com.example.quanlydaotao.dto;

import java.util.List;

public class TrainingStatsDTO {
    private int internsEnrolled;
    private int graduatingInterns;
    private int internsFailed;
    private Double rate;
    private int internsCurrentlyPracticing;
    private int internsQuitInternship;
    private double averageGraduationScore;
    public TrainingStatsDTO() {}
    private List<AverageOfSubjectDTO> averageOfSubject;
    public TrainingStatsDTO(int internsEnrolled, int graduatingInterns,
                            int internsFailed, Double rate,
                            int internsCurrentlyPracticing,
                            int internsQuitInternship,
                            double averageGraduationScore) {
        this.internsEnrolled = internsEnrolled;
        this.graduatingInterns = graduatingInterns;
        this.internsFailed = internsFailed;
        this.rate = rate;
        this.internsQuitInternship = internsQuitInternship;
        this.internsCurrentlyPracticing = internsCurrentlyPracticing;
        this.averageGraduationScore = averageGraduationScore;
    }

    public TrainingStatsDTO(int internsEnrolled, int graduatingInterns,
                            int internsFailed, Double rate,
                            int internsCurrentlyPracticing,
                            int internsQuitInternship,
                            double averageGraduationScore, List<AverageOfSubjectDTO> averageOfSubject) {
        this.internsEnrolled = internsEnrolled;
        this.graduatingInterns = graduatingInterns;
        this.internsFailed = internsFailed;
        this.rate = rate;
        this.internsQuitInternship = internsQuitInternship;
        this.internsCurrentlyPracticing = internsCurrentlyPracticing;
        this.averageGraduationScore = averageGraduationScore;
        this.averageOfSubject = averageOfSubject;
    }

    public int getInternsEnrolled() {
        return internsEnrolled;
    }

    public void setInternsEnrolled(int internsEnrolled) {
        this.internsEnrolled = internsEnrolled;
    }

    public int getGraduatingInterns() {
        return graduatingInterns;
    }

    public void setGraduatingInterns(int graduatingInterns) {
        this.graduatingInterns = graduatingInterns;
    }

    public int getInternsFailed() {
        return internsFailed;
    }

    public void setInternsFailed(int internsFailed) {
        this.internsFailed = internsFailed;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public int getInternsCurrentlyPracticing() {
        return internsCurrentlyPracticing;
    }

    public void setInternsCurrentlyPracticing(int internsCurrentlyPracticing) {
        this.internsCurrentlyPracticing = internsCurrentlyPracticing;
    }



    public double getAverageGraduationScore() {
        return averageGraduationScore;
    }

    public void setAverageGraduationScore(double averageGraduationScore) {
        this.averageGraduationScore = averageGraduationScore;
    }

    public int getInternsQuitInternship() {
        return internsQuitInternship;
    }

    public void setInternsQuitInternship(int internsQuitInternship) {
        this.internsQuitInternship = internsQuitInternship;
    }

    public List<AverageOfSubjectDTO> getAverageOfSubject() {
        return averageOfSubject;
    }

    public void setAverageOfSubject(List<AverageOfSubjectDTO> averageOfSubject) {
        this.averageOfSubject = averageOfSubject;
    }
}
