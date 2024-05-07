package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentFormDTO {
    private Long idUser;
    private RecruitmentRequest recruitmentRequest;
    private List<RecruitmentRequestDetail> details;

    public RecruitmentRequest getRecruitmentRequest() {
        return recruitmentRequest;
    }

    public RecruitmentFormDTO setRecruitmentRequest(RecruitmentRequest recruitmentRequest) {
        this.recruitmentRequest = recruitmentRequest;
        return this;
    }

    public List<RecruitmentRequestDetail> getDetails() {
        return details;
    }

    public RecruitmentFormDTO setDetails(List<RecruitmentRequestDetail> details) {
        this.details = details;
        return this;
    }

    public Long getIdUser() {
        return idUser;
    }

    public RecruitmentFormDTO setIdUser(Long id) {
        this.idUser = id;
        return this;
    }
}
