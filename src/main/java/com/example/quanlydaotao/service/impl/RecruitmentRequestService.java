package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.repository.IRecruitmentRequestDetailRepository;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import com.example.quanlydaotao.repository.IUserRecruitmentActionRepository;
import com.example.quanlydaotao.service.IRecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruitmentRequestService implements IRecruitmentRequestService {
    @Autowired
    private IRecruitmentRequestRepository recruitmentRequestRepository;
    @Autowired
    private IUserRecruitmentActionRepository userRecruitmentActionRepository;
    @Override
    public Iterable<RecruitmentRequest> getAllRecruitmentRequests() {
        Iterable<RecruitmentRequest> recruitmentRequests = recruitmentRequestRepository.getAll();
        return recruitmentRequests;
    }

    @Override
    public Optional<RecruitmentRequest> findRecruitmentRequestById(long id) {
        Optional<RecruitmentRequest> recruitmentRequest = recruitmentRequestRepository.findById(id);
        return recruitmentRequest;
    }

}
