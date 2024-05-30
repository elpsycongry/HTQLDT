package com.example.quanlydaotao.dto;

public class ProcessDTO {
    private long requestId;
    private String requestName;
    private String requestCreator;
    private boolean detAccept;

    private long planId;
    private String planName;
    private boolean decanAccept;

    private int applicants;
    private int training;
    private int intern;

    private String reason;
    private int step;

    public String getRequestName() {
        return requestName;
    }

    public ProcessDTO setRequestName(String requestName) {
        this.requestName = requestName;
        return this;
    }

    public String getRequestCreator() {
        return requestCreator;
    }

    public ProcessDTO setRequestCreator(String requestCreator) {
        this.requestCreator = requestCreator;
        return this;
    }



    public int getApplicants() {
        return applicants;
    }

    public ProcessDTO setApplicants(int applicants) {
        this.applicants = applicants;
        return this;
    }

    public int getTraining() {
        return training;
    }

    public ProcessDTO setTraining(int training) {
        this.training = training;
        return this;
    }

    public int getIntern() {
        return intern;
    }

    public ProcessDTO setIntern(int intern) {
        this.intern = intern;
        return this;
    }

    public long getRequestId() {
        return requestId;
    }

    public ProcessDTO setRequestId(long requestId) {
        this.requestId = requestId;
        return this;
    }

    public long getPlanId() {
        return planId;
    }

    public ProcessDTO setPlanId(long planId) {
        this.planId = planId;
        return this;
    }

    public boolean isDetAccept() {
        return detAccept;
    }

    public ProcessDTO setDetAccept(boolean detAccept) {
        this.detAccept = detAccept;
        return this;
    }

    public boolean isDecanAccept() {
        return decanAccept;
    }

    public ProcessDTO setDecanAccept(boolean decanAccept) {
        this.decanAccept = decanAccept;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public ProcessDTO setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getPlanName() {
        return planName;
    }

    public ProcessDTO setPlanName(String planName) {
        this.planName = planName;
        return this;
    }

    public int getStep() {
        return step;
    }

    public ProcessDTO setStep(int step) {
        this.step = step;
        return this;
    }
}
