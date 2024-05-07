package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.RecruitmentRequestDetail;

import java.util.Optional;

public interface IRecruitmentRequestDetailService {
    Optional<RecruitmentRequestDetail> findByRecruitmentId(long id);
    Iterable<RecruitmentRequestDetail> findAll();
}
