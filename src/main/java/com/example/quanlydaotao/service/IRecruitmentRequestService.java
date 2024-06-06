package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.RecruitmentRequest;

import java.util.Optional;

public interface IRecruitmentRequestService {
    Iterable<RecruitmentRequest> getAllRecruitmentRequests();
    Optional<RecruitmentRequest> findRecruitmentRequestById(long id);
    String getRecruitmentNameById(long id);

}
