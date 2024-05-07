package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentFormDTO {
    private RecruitmentRequest recruitmentRequest;
    private List<RecruitmentRequestDetail> languages = new ArrayList<>();

    public RecruitmentRequest getRecruitmentRequest() {
        return recruitmentRequest;
    }

    public RecruitmentFormDTO setRecruitmentRequest(RecruitmentRequest recruitmentRequest) {
        this.recruitmentRequest = recruitmentRequest;
        return this;
    }

    public List<RecruitmentRequestDetail> getLanguages() {
        return languages;
    }

    public RecruitmentFormDTO setLanguages(List<RecruitmentRequestDetail> languages) {
        this.languages = languages;
        return this;
    }
}
