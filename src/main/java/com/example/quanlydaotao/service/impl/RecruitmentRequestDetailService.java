package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import com.example.quanlydaotao.repository.IRecruitmentRequestDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitmentRequestDetailService {
    @Autowired
    private IRecruitmentRequestDetailRepository detailRepository;
    public void saveDetail(RecruitmentRequestDetail requestDetail) {
        detailRepository.save(requestDetail);
    }
}
