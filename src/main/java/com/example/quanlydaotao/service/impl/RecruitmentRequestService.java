package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.RecruitmentFormDTO;
import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentRequestService {
    @Autowired
    private IRecruitmentRequestRepository iRecruitmentRequestRepository;
    @Autowired
    private RecruitmentRequestDetailService iRecruitmentRequestDetailService;

    public void createRecruitmentRequest(RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentRequest request = iRecruitmentRequestRepository.save(recruitmentFormDTO.getRecruitmentRequest());
        List<RecruitmentRequestDetail> requestDetails = recruitmentFormDTO.getDetails();
        for (RecruitmentRequestDetail detail : requestDetails) {
            detail.setRecruitmentRequest(request);
            iRecruitmentRequestDetailService.saveDetail(detail);
        }

    }
}
