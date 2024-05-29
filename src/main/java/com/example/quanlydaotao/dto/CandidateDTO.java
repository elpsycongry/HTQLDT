package com.example.quanlydaotao.dto;

public class CandidateDTO {
    private int totalCandidate;
    private int haveNotInterviewedYet;
    private int candidatePass;
    private int candidateFail;
    public CandidateDTO() {}
    public CandidateDTO(int totalCandidate, int haveNotInterviewedYet, int candidatePass, int candidateFail) {
        this.totalCandidate = totalCandidate;
        this.haveNotInterviewedYet = haveNotInterviewedYet;
    }

    public int getTotalCandidate() {
        return totalCandidate;
    }

    public void setTotalCandidate(int totalCandidate) {
        this.totalCandidate = totalCandidate;
    }

    public int getHaveNotInterviewedYet() {
        return haveNotInterviewedYet;
    }

    public void setHaveNotInterviewedYet(int haveNotInterviewedYet) {
        this.haveNotInterviewedYet = haveNotInterviewedYet;
    }

    public int getCandidatePass() {
        return candidatePass;
    }

    public void setCandidatePass(int candidatePass) {
        this.candidatePass = candidatePass;
    }

    public int getCandidateFail() {
        return candidateFail;
    }

    public void setCandidateFail(int candidateFail) {
        this.candidateFail = candidateFail;
    }
}
