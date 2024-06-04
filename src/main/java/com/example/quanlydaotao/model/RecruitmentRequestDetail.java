package com.example.quanlydaotao.model;

import jakarta.persistence.*;

@Entity
public class RecruitmentRequestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruitment_request_id", referencedColumnName = "id")
    private RecruitmentRequest recruitmentRequest;

    private String type;
    private int quantity;

    public Long getId() {
        return id;
    }

    public RecruitmentRequestDetail setId(Long id) {
        this.id = id;
        return this;
    }

    public RecruitmentRequest getRecruitmentRequest() {
        return recruitmentRequest;
    }

    public RecruitmentRequestDetail setRecruitmentRequest(RecruitmentRequest recruitmentRequest) {
        this.recruitmentRequest = recruitmentRequest;
        return this;
    }

    public String getType() {
        return type;
    }

    public RecruitmentRequestDetail setType(String type) {
        this.type = type;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public RecruitmentRequestDetail setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
