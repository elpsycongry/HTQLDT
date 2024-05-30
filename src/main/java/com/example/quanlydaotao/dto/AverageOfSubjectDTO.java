package com.example.quanlydaotao.dto;

public class AverageOfSubjectDTO {
    private String subjectName;
    private Double average;
    public AverageOfSubjectDTO() {}
    public AverageOfSubjectDTO(String subjectName, Double average) {}

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
