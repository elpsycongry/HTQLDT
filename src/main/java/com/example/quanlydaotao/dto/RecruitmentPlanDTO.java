package com.example.quanlydaotao.dto;

public class RecruitmentPlanDTO {
    private  String name;
    private String status;

    public RecruitmentPlanDTO (String name,String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public RecruitmentPlanDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RecruitmentPlanDTO setStatus(String status) {
        this.status = status;
        return this;
    }
}
