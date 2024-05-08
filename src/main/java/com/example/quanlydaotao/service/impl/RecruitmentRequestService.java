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
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserRecruitmentActionService userActionService;
    @Autowired
    private IRecruitmentRequestRepository iRecruitmentRequestRepository;
    @Autowired
    private RecruitmentRequestDetailService iRecruitmentRequestDetailService;
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

    public void createRecruitmentRequest(RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentRequest request = recruitmentFormDTO.getRecruitmentRequest();
        request.setStatus("Đang chờ");
        request = iRecruitmentRequestRepository.save(request);

        createUserRecruitmentAction(recruitmentFormDTO.getIdUser(), request, UserAction.Demand.toString());

        List<RecruitmentRequestDetail> requestDetails = recruitmentFormDTO.getDetails();
        for (RecruitmentRequestDetail detail : requestDetails) {
            detail.setRecruitmentRequest(request);
            iRecruitmentRequestDetailService.saveDetail(detail);
        }
    }

    public void updateStatusRecruitment(long idRecruitment, long idUser, String status, String reason) {
        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(idRecruitment).get();
        recruitmentRequest.setStatus(status);
        recruitmentRequest.setReason(reason);
        recruitmentRequest = recruitmentRequestRepository.save(recruitmentRequest);

        String action = UserAction.Denied.toString();
        createUserRecruitmentAction(idUser, recruitmentRequest, action);
    }

    private void createUserRecruitmentAction(long idUser, RecruitmentRequest recruitmentRequest, String action) {
        Users user = usersService.findById(idUser).get();
        UserRecruitmentAction userAction = new UserRecruitmentAction();
        userAction.setUser(user)
                .setRecruitmentRequest(recruitmentRequest)
                .setUser(user)
                .setAction(action);
        userActionService.save(userAction);
    }
}
