package com.example.quanlydaotao.dto;

public class RecruitmentSearchDTO {

    private String name;
    private String status;

    public RecruitmentSearchDTO(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
