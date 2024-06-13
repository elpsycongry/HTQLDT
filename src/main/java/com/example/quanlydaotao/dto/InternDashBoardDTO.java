package com.example.quanlydaotao.dto;

public class InternDashBoardDTO {
    private int totalIntern;
    private int internTraining;
    private int internPass;
    private int internFail;
    public InternDashBoardDTO() {}
    public InternDashBoardDTO(int totalIntern, int internTraining, int internPass, int internFail) {
        this.totalIntern = totalIntern;
        this.internTraining = internTraining;
        this.internPass = internPass;
        this.internFail = internFail;
    }

    public int getTotalIntern() {
        return totalIntern;
    }

    public void setTotalIntern(int totalIntern) {
        this.totalIntern = totalIntern;
    }

    public int getInternTraining() {
        return internTraining;
    }

    public void setInternTraining(int internTraining) {
        this.internTraining = internTraining;
    }

    public int getInternPass() {
        return internPass;
    }

    public void setInternPass(int internPass) {
        this.internPass = internPass;
    }

    public int getInternFail() {
        return internFail;
    }

    public void setInternFail(int internFail) {
        this.internFail = internFail;
    }
}
