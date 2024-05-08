package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.repository.IRecruitmentRequestDetailRepository;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import com.example.quanlydaotao.repository.IUserRecruitmentActionRepository;
import com.example.quanlydaotao.dto.RecruitmentFormDTO;
import com.example.quanlydaotao.dto.UserAction;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import com.example.quanlydaotao.model.Users;
import com.example.quanlydaotao.service.IRecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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

    @Autowired
    private IRecruitmentRequestDetailRepository recruitmentRequestDetailRepository;
    @Autowired
    private IUserRecruitmentActionRepository iUserRecruitmentActionRepository;
    @Autowired
    private IRecruitmentRequestDetailRepository iRecruitmentRequestDetailRepository;


    public void createRecruitmentRequest(RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentRequest request = recruitmentFormDTO.getRecruitmentRequest();
        request.setStatus("Đang chờ");
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

    public void updateRecruitmentRequest(RecruitmentFormDTO recruitmentFormDTO, long id) throws Exception {
        Iterable<RecruitmentRequest> recruitmentRequests = recruitmentRequestRepository.findAll();
        for (RecruitmentRequest recruitmentRequest : recruitmentRequests) {
            if (recruitmentFormDTO.getRecruitmentRequest().getName().equals(recruitmentRequest.getName())) {
                if (id != recruitmentRequest.getId()) {
                    throw new Exception();
                }
            }
        }
        Iterable<RecruitmentRequestDetail> recruitmentRequestDetails = iRecruitmentRequestDetailRepository.findByRecruitmentRequestId(recruitmentFormDTO.getRecruitmentRequest().getId());
        Optional<UserRecruitmentAction> userRecruitmentAction = iUserRecruitmentActionRepository.findByRecruitmentRequestId(id);
        DeleteAllRecruitment(id, userRecruitmentAction);
        SaveRecruitment(recruitmentFormDTO, recruitmentRequestDetails);
    }

    private void SaveRecruitment(RecruitmentFormDTO recruitmentFormDTO, Iterable<RecruitmentRequestDetail> recruitmentRequestDetails) {
        RecruitmentRequest request = recruitmentFormDTO.getRecruitmentRequest();
        iRecruitmentRequestRepository.saveAndFlush(request);
        Users user = usersService.findById(recruitmentFormDTO.getIdUser()).get();
        UserRecruitmentAction userAction = new UserRecruitmentAction();
        userAction.setUser(user).setRecruitmentRequest(request).setUser(user).setAction(UserAction.Demand.toString());
        iUserRecruitmentActionRepository.save(userAction);
        List<RecruitmentRequestDetail> requestDetailsOld = new ArrayList<>();
        for (RecruitmentRequestDetail detail : recruitmentRequestDetails) {
            requestDetailsOld.add(detail);
        }
        List<RecruitmentRequestDetail> requestDetails = recruitmentFormDTO.getDetails();
        for (RecruitmentRequestDetail detail : requestDetails) {
            detail.setRecruitmentRequest(request);
            iRecruitmentRequestDetailService.saveDetail(detail);
        }

    }

    private void DeleteAllRecruitment(long id, Optional<UserRecruitmentAction> userRecruitmentAction) {
        iUserRecruitmentActionRepository.delete(userRecruitmentAction.get());
        iRecruitmentRequestDetailRepository.deleteAllByRecruitmentRequestId(id);
    }
}
