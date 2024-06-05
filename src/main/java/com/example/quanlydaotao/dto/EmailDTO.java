package com.example.quanlydaotao.dto;

public class EmailDTO {
    private String to_name;
    private String name_personal_needs;
    private String date_start;
    private String date_end;
    private int intern_needs;
    private int intern_need_hand_over;
    private int total_intern;
    private int pass_intern;
    private int training_intern;
    private int hand_over_intern;
    private int fail_intern;
    private int not_training_intern;

    public String getTo_name() {
        return to_name;
    }

    public EmailDTO setTo_name(String to_name) {
        this.to_name = to_name;
        return this;
    }

    public String getName_personal_needs() {
        return name_personal_needs;
    }

    public EmailDTO setName_personal_needs(String name_personal_needs) {
        this.name_personal_needs = name_personal_needs;
        return this;
    }

    public String getDate_start() {
        return date_start;
    }

    public EmailDTO setDate_start(String date_start) {
        this.date_start = date_start;
        return this;
    }

    public String getDate_end() {
        return date_end;
    }

    public EmailDTO setDate_end(String date_end) {
        this.date_end = date_end;
        return this;
    }

    public int getIntern_needs() {
        return intern_needs;
    }

    public EmailDTO setIntern_needs(int intern_needs) {
        this.intern_needs = intern_needs;
        return this;
    }

    public int getIntern_need_hand_over() {
        return intern_need_hand_over;
    }

    public EmailDTO setIntern_need_hand_over(int intern_need_hand_over) {
        this.intern_need_hand_over = intern_need_hand_over;
        return this;
    }

    public int getTotal_intern() {
        return total_intern;
    }

    public EmailDTO setTotal_intern(int total_intern) {
        this.total_intern = total_intern;
        return this;
    }

    public int getPass_intern() {
        return pass_intern;
    }

    public EmailDTO setPass_intern(int pass_intern) {
        this.pass_intern = pass_intern;
        return this;
    }

    public int getTraining_intern() {
        return training_intern;
    }

    public EmailDTO setTraining_intern(int training_intern) {
        this.training_intern = training_intern;
        return this;
    }

    public int getHand_over_intern() {
        return hand_over_intern;
    }

    public EmailDTO setHand_over_intern(int hand_over_intern) {
        this.hand_over_intern = hand_over_intern;
        return this;
    }

    public int getFail_intern() {
        return fail_intern;
    }

    public EmailDTO setFail_intern(int fail_intern) {
        this.fail_intern = fail_intern;
        return this;
    }

    public int getNot_training_intern() {
        return not_training_intern;
    }

    public EmailDTO setNot_training_intern(int not_training_intern) {
        this.not_training_intern = not_training_intern;
        return this;
    }
}
