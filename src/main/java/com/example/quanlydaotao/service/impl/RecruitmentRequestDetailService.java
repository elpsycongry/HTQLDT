package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import com.example.quanlydaotao.repository.IRecruitmentRequestDetailRepository;
import com.example.quanlydaotao.service.IRecruitmentRequestDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruitmentRequestDetailService implements IRecruitmentRequestDetailService {
    @Autowired
    private IRecruitmentRequestDetailRepository recruitmentRequestDetailRepository;
    @Override
    public Optional<RecruitmentRequestDetail> findById(long id) {
        return recruitmentRequestDetailRepository.findByRecruitmentRequestId(id);
    }

    @Override
    public Iterable<RecruitmentRequestDetail> findAll() {
        return recruitmentRequestDetailRepository.findAll();
    }

    public void saveDetail(RecruitmentRequestDetail requestDetail) {
            recruitmentRequestDetailRepository.save(requestDetail);
    }
}
