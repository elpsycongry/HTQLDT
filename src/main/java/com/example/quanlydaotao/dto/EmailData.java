package com.example.quanlydaotao.dto;

public class EmailData {
    private String toEmail;
    private String toName;
    private String namePersonalNeeds;
    private String dateStart;
    private String dateEnd;
    private int internNeeds;// số lượng đầu vào
    private int internNeedHandOver;// số lượng đầu ra tts
    private int linkProgress;
    private int totalIntern;// tổng số ứng viên
    private int passIntern;
    private int trainingIntern;
    private int handOverIntern;// số lượng đã bàn giao
    private int failIntern;
    private int notTrainingIntern;
    private int isRequest;

    public int getIsRequest() {
        return isRequest;
    }

    public EmailData setIsRequest(int isRequest) {
        this.isRequest = isRequest;
        return this;
    }

    public String getToEmail() {
        return toEmail;
    }

    public EmailData setToEmail(String toEmail) {
        this.toEmail = toEmail;
        return this;
    }

    public String getToName() {
        return toName;
    }

    public EmailData setToName(String toName) {
        this.toName = toName;
        return this;
    }

    public String getNamePersonalNeeds() {
        return namePersonalNeeds;
    }

    public EmailData setNamePersonalNeeds(String namePersonalNeeds) {
        this.namePersonalNeeds = namePersonalNeeds;
        return this;
    }

    public String getDateStart() {
        return dateStart;
    }

    public EmailData setDateStart(String dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public EmailData setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public int getInternNeeds() {
        return internNeeds;
    }

    public EmailData setInternNeeds(int internNeeds) {
        this.internNeeds = internNeeds;
        return this;
    }

    public int getInternNeedHandOver() {
        return internNeedHandOver;
    }

    public EmailData setInternNeedHandOver(int internNeedHandOver) {
        this.internNeedHandOver = internNeedHandOver;
        return this;
    }

    public int getLinkProgress() {
        return linkProgress;
    }

    public EmailData setLinkProgress(int linkProgress) {
        this.linkProgress = linkProgress;
        return this;
    }

    public int getTotalIntern() {
        return totalIntern;
    }

    public EmailData setTotalIntern(int totalIntern) {
        this.totalIntern = totalIntern;
        return this;
    }

    public int getPassIntern() {
        return passIntern;
    }

    public EmailData setPassIntern(int passIntern) {
        this.passIntern = passIntern;
        return this;
    }

    public int getTrainingIntern() {
        return trainingIntern;
    }

    public EmailData setTrainingIntern(int trainingIntern) {
        this.trainingIntern = trainingIntern;
        return this;
    }

    public int getHandOverIntern() {
        return handOverIntern;
    }

    public EmailData setHandOverIntern(int handOverIntern) {
        this.handOverIntern = handOverIntern;
        return this;
    }

    public int getFailIntern() {
        return failIntern;
    }

    public EmailData setFailIntern(int failIntern) {
        this.failIntern = failIntern;
        return this;
    }

    public int getNotTrainingIntern() {
        return notTrainingIntern;
    }

    public EmailData setNotTrainingIntern(int notTrainingIntern) {
        this.notTrainingIntern = notTrainingIntern;
        return this;
    }
}
