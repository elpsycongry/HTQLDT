package com.example.quanlydaotao.dto;

public class TrainingStatsDTO {
    private String internsEnrolled;
    private String graduatingInterns;
    private String internsFailed;
    private Double rate;
    private String internsCurrentlyPracticing;
    private String internsQuitInternship;
    private double averageGraduationScore;
    public TrainingStatsDTO() {}
    public TrainingStatsDTO(String internsEnrolled, String graduatingInterns,
                            String internsFailed, Double rate,
                            String internsCurrentlyPracticing,
                            String internsQuitInternship,
                            double averageGraduationScore) {
        this.internsEnrolled = internsEnrolled;
        this.graduatingInterns = graduatingInterns;
        this.internsFailed = internsFailed;
        this.rate = rate;
        this.internsQuitInternship = internsQuitInternship;
        this.internsCurrentlyPracticing = internsCurrentlyPracticing;
        this.averageGraduationScore = averageGraduationScore;
    }

    public String getInternsEnrolled() {
        return internsEnrolled;
    }

    public void setInternsEnrolled(String internsEnrolled) {
        this.internsEnrolled = internsEnrolled;
    }

    public String getGraduatingInterns() {
        return graduatingInterns;
    }

    public void setGraduatingInterns(String graduatingInterns) {
        this.graduatingInterns = graduatingInterns;
    }

    public String getInternsFailed() {
        return internsFailed;
    }

    public void setInternsFailed(String internsFailed) {
        this.internsFailed = internsFailed;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getInternsCurrentlyPracticing() {
        return internsCurrentlyPracticing;
    }

    public void setInternsCurrentlyPracticing(String internsCurrentlyPracticing) {
        this.internsCurrentlyPracticing = internsCurrentlyPracticing;
    }



    public double getAverageGraduationScore() {
        return averageGraduationScore;
    }

    public void setAverageGraduationScore(double averageGraduationScore) {
        this.averageGraduationScore = averageGraduationScore;
    }

    public String getInternsQuitInternship() {
        return internsQuitInternship;
    }

    public void setInternsQuitInternship(String internsQuitInternship) {
        this.internsQuitInternship = internsQuitInternship;
    }
}
