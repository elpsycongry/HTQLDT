package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.RecruitmentRequest;

import java.util.List;
import java.util.Optional;

public interface IRecruitmentRequestService {
    Iterable<RecruitmentRequest> getAllRecruitmentRequests();
    Optional<RecruitmentRequest> findRecruitmentRequestById(long id);

}