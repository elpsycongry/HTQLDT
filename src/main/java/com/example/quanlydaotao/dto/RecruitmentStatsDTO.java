package com.example.quanlydaotao.dto;

public class RecruitmentStatsDTO {
    private int totalCV;
    private int totalInterviewCV;
    private int candidatesInterview;
    private int candidatesDoNotInterview;
    private int candidatesPass;
    private int candidatesFail;
    private int candidatesAcceptJob;
    private int candidatesRejectJob;
    private int candidatesAcceptJobYet;
    public RecruitmentStatsDTO(){}
    public RecruitmentStatsDTO(int totalCV, int totalInterviewCV, int candidatesInterview
    , int candidatesDoNotInterview, int candidatesPass, int candidatesFail
    , int candidatesAcceptJob, int candidatesRejectJob, int candidatesAcceptJobYet) {
        this.totalCV = totalCV;
        this.totalInterviewCV = totalInterviewCV;
        this.candidatesInterview = candidatesInterview;
        this.candidatesDoNotInterview = candidatesDoNotInterview;
        this.candidatesPass = candidatesPass;
        this.candidatesFail = candidatesFail;
        this.candidatesAcceptJob = candidatesAcceptJob;
        this.candidatesRejectJob = candidatesRejectJob;
        this.candidatesAcceptJobYet = candidatesAcceptJobYet;
    }

    public int getTotalCV() {
        return totalCV;
    }

    public void setTotalCV(int totalCV) {
        this.totalCV = totalCV;
    }

    public int getTotalInterviewCV() {
        return totalInterviewCV;
    }

    public void setTotalInterviewCV(int totalInterviewCV) {
        this.totalInterviewCV = totalInterviewCV;
    }

    public int getCandidatesInterview() {
        return candidatesInterview;
    }

    public void setCandidatesInterview(int candidatesInterview) {
        this.candidatesInterview = candidatesInterview;
    }

    public int getCandidatesDoNotInterview() {
        return candidatesDoNotInterview;
    }

    public void setCandidatesDoNotInterview(int candidatesDoNotInterview) {
        this.candidatesDoNotInterview = candidatesDoNotInterview;
    }

    public int getCandidatesPass() {
        return candidatesPass;
    }

    public void setCandidatesPass(int candidatesPass) {
        this.candidatesPass = candidatesPass;
    }

    public int getCandidatesFail() {
        return candidatesFail;
    }

    public void setCandidatesFail(int candidatesFail) {
        this.candidatesFail = candidatesFail;
    }

    public int getCandidatesAcceptJob() {
        return candidatesAcceptJob;
    }

    public void setCandidatesAcceptJob(int candidatesAcceptJob) {
        this.candidatesAcceptJob = candidatesAcceptJob;
    }

    public int getCandidatesRejectJob() {
        return candidatesRejectJob;
    }

    public void setCandidatesRejectJob(int candidatesRejectJob) {
        this.candidatesRejectJob = candidatesRejectJob;
    }

    public int getCandidatesAcceptJobYet() {
        return candidatesAcceptJobYet;
    }

    public void setCandidatesAcceptJobYet(int candidatesAcceptJobYet) {
        this.candidatesAcceptJobYet = candidatesAcceptJobYet;
    }
}
