package com.example.quanlydaotao.dto;

public class EmailDTO {
    private String toEmail;
    private String toName;
    private String namePersonalNeeds;
    private String dateStart;
    private String dateEnd;
    private int internNeeds;
    private int internNeedHandOver;
    private int linkProgress;
    private int totalIntern;
    private int passIntern;
    private int trainingIntern;
    private int handOverIntern;
    private int failIntern;
    private int notTrainingIntern;

    public String getToName() {
        return toName;
    }
    public String getToEmail() {
        return toEmail;
    }

    public EmailDTO setToEmail(String to_email) {
        this.toEmail = to_email;
        return this;
    }
    public EmailDTO setToName(String to_name) {
        this.toName = to_name;
        return this;
    }

    public String getNamePersonalNeeds() {
        return namePersonalNeeds;
    }

    public EmailDTO setNamePersonalNeeds(String name_personal_needs) {
        this.namePersonalNeeds = name_personal_needs;
        return this;
    }

    public String getDateStart() {
        return dateStart;
    }

    public EmailDTO setDateStart(String date_start) {
        this.dateStart = date_start;
        return this;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public EmailDTO setDateEnd(String date_end) {
        this.dateEnd = date_end;
        return this;
    }

    public int getInternNeeds() {
        return internNeeds;
    }

    public EmailDTO setInternNeeds(int intern_needs) {
        this.internNeeds = intern_needs;
        return this;
    }

    public int getLinkProgress() {
        return linkProgress;
    }

    public EmailDTO setLinkProgress(int linkProgress) {
        this.linkProgress = linkProgress;
        return this;
    }

    public int getInternNeedHandOver() {
        return internNeedHandOver;
    }

    public EmailDTO setInternNeedHandOver(int intern_need_hand_over) {
        this.internNeedHandOver = intern_need_hand_over;
        return this;
    }

    public int getTotalIntern() {
        return totalIntern;
    }

    public EmailDTO setTotalIntern(int total_intern) {
        this.totalIntern = total_intern;
        return this;
    }

    public int getPassIntern() {
        return passIntern;
    }

    public EmailDTO setPassIntern(int pass_intern) {
        this.passIntern = pass_intern;
        return this;
    }

    public int getTrainingIntern() {
        return trainingIntern;
    }

    public EmailDTO setTraining_intern(int training_intern) {
        this.trainingIntern = training_intern;
        return this;
    }

    public int getHandOverIntern() {
        return handOverIntern;
    }

    public EmailDTO setHandOverIntern(int hand_over_intern) {
        this.handOverIntern = hand_over_intern;
        return this;
    }

    public int getFail_intern() {
        return failIntern;
    }

    public EmailDTO setFail_intern(int fail_intern) {
        this.failIntern = fail_intern;
        return this;
    }

    public int getNot_training_intern() {
        return notTrainingIntern;
    }

    public EmailDTO setNot_training_intern(int not_training_intern) {
        this.notTrainingIntern = not_training_intern;
        return this;
    }
}
