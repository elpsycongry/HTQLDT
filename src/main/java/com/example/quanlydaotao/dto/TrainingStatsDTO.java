package com.example.quanlydaotao.dto;

public class TrainingStatsDTO {
    private String internsEnrolled;
    private String graduatingInterns;
    private String internsFailed;
    private int rate;
    private String internsCurrentlyPracticing;
    private double averageGraduationScore;
    public TrainingStatsDTO() {}
    public TrainingStatsDTO(String internsEnrolled, String graduatingInterns,
                            String internsFailed, int rate,
                            String internsCurrentlyPracticing,
                            double averageGraduationScore) {
        this.internsEnrolled = internsEnrolled;
        this.graduatingInterns = graduatingInterns;
        this.internsFailed = internsFailed;
        this.rate = rate;
        this.internsCurrentlyPracticing = internsCurrentlyPracticing;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
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
}
