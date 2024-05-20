package com.example.quanlydaotao.model;

import jakarta.persistence.*;

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
    private boolean isInterview;
    private String comment;
    private String note;
    private boolean finalResult;
    private String status;

}
