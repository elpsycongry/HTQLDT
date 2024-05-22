package com.example.quanlydaotao.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Intern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruitment_plan_id", referencedColumnName = "id")
    private RecruitmentPlan recruitmentPlan;

    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;

    private String linkCv;
    private LocalDateTime interviewTime;
    private boolean checkInterview;
    private String comment;
    private String note;
    private boolean finalResult;
    private String status;
    private String scoreTest;
    private String scoreInterview;

    public String getScoreTest() {
        return scoreTest;
    }

    public Intern setScoreTest(String scoreTest) {
        this.scoreTest = scoreTest;
        return this;
    }

    public String getScoreInterview() {
        return scoreInterview;
    }

    public Intern setScoreInterview(String scoreInterview) {
        this.scoreInterview = scoreInterview;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Intern setStatus(String status) {
        this.status = status;
        return this;
    }

    public boolean isFinalResult() {
        return finalResult;
    }

    public Intern setFinalResult(boolean finalResult) {
        this.finalResult = finalResult;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Intern setNote(String note) {
        this.note = note;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Intern setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public boolean isCheckInterview() {
        return checkInterview;
    }

    public Intern setCheckInterview(boolean checkInterview) {
        this.checkInterview = checkInterview;
        return this;
    }

    public LocalDateTime getInterviewTime() {
        return interviewTime;
    }

    public Intern setInterviewTime(LocalDateTime interviewTime) {
        this.interviewTime = interviewTime;
        return this;
    }

    public String getLinkCv() {
        return linkCv;
    }

    public Intern setLinkCv(String linkCv) {
        this.linkCv = linkCv;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Intern setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Intern setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public Intern setName(String name) {
        this.name = name;
        return this;
    }

    public RecruitmentPlan getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public Intern setRecruitmentPlan(RecruitmentPlan recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Intern setId(Long id) {
        this.id = id;
        return this;
    }
}
