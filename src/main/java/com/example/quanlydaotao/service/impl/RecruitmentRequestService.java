package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.repository.IRecruitmentRequestDetailRepository;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import com.example.quanlydaotao.repository.IUserRecruitmentActionRepository;

import com.example.quanlydaotao.dto.RecruitmentFormDTO;
import com.example.quanlydaotao.dto.UserAction;
import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.model.Users;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;

import com.example.quanlydaotao.service.IRecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    @Autowired
    private UsersService usersService;
    @Autowired
    private UserRecruitmentActionService userActionService;
    @Autowired
    private IRecruitmentRequestRepository iRecruitmentRequestRepository;
    @Autowired
    private RecruitmentRequestDetailService iRecruitmentRequestDetailService;

    public void createRecruitmentRequest(RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentRequest request = recruitmentFormDTO.getRecruitmentRequest();
        request = iRecruitmentRequestRepository.save(request);

        Users user = usersService.findById(recruitmentFormDTO.getIdUser()).get();
        UserRecruitmentAction userAction = new UserRecruitmentAction();
        userAction.setUser(user).setRecruitmentRequest(request)
                                .setUser(user)
                                        .setAction(UserAction.Demand.toString());
        userActionService.save(userAction);

        List<RecruitmentRequestDetail> requestDetails = recruitmentFormDTO.getDetails();
        for (RecruitmentRequestDetail detail : requestDetails) {
            detail.setRecruitmentRequest(request);
            iRecruitmentRequestDetailService.saveDetail(detail);
        }

    }
}
