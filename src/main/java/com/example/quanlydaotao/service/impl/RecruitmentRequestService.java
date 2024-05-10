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
import com.example.quanlydaotao.repository.IUserRepository;
import com.example.quanlydaotao.service.IRecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentRequestService implements IRecruitmentRequestService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserRecruitmentActionService userActionService;
    @Autowired
    private IRecruitmentRequestRepository iRecruitmentRequestRepository;
    @Autowired
    private RecruitmentRequestDetailService iRecruitmentRequestDetailService;
    @Autowired
    private IUserRecruitmentActionRepository iUserRecruitmentActionRepository;
    @Autowired
    private IRecruitmentRequestDetailRepository iRecruitmentRequestDetailRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Iterable<RecruitmentRequest> getAllRecruitmentRequests() {
        Iterable<RecruitmentRequest> recruitmentRequests = iRecruitmentRequestRepository.getAll();
        return recruitmentRequests;
    }

    @Override
    public Optional<RecruitmentRequest> findRecruitmentRequestById(long id) {
        Optional<RecruitmentRequest> recruitmentRequest = iRecruitmentRequestRepository.findById(id);
        return recruitmentRequest;
    }


    public void createRecruitmentRequest(RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentRequest request = recruitmentFormDTO.getRecruitmentRequest();
        request.setStatus("Đã gửi");
        Optional<Users> users = userRepository.findById(recruitmentFormDTO.getIdUser());
        request.setUsers(users.get());
        LocalDateTime localDateTime = LocalDateTime.now();
        int day = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
        request.setDateStart(dateTime);
        request = iRecruitmentRequestRepository.save(request);

        createUserRecruitmentAction(recruitmentFormDTO.getIdUser(), request, UserAction.Demand.toString());

        List<RecruitmentRequestDetail> requestDetails = recruitmentFormDTO.getDetails();

        for (RecruitmentRequestDetail detail : requestDetails) {
            detail.setRecruitmentRequest(request);
            iRecruitmentRequestDetailService.saveDetail(detail);
        }
    }

    public void updateStatusRecruitment(long idRecruitment, long idUser, String status, String reason) {
        RecruitmentRequest recruitmentRequest = iRecruitmentRequestRepository.findById(idRecruitment).get();
        recruitmentRequest.setStatus(status);
        recruitmentRequest.setReason(reason);
        recruitmentRequest = iRecruitmentRequestRepository.save(recruitmentRequest);

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

    public void updateRecruitmentRequest(RecruitmentFormDTO recruitmentFormDTO, long id) throws Exception {
        Iterable<RecruitmentRequest> recruitmentRequests = iRecruitmentRequestRepository.findAll();
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
        Optional<Users> users = userRepository.findById(recruitmentFormDTO.getIdUser());
        request.setUsers(users.get());
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
    @Override
    public Iterable<Object[]> findByName(String name) {
        if (name == null || name.isEmpty()) {
            return iRecruitmentRequestRepository.finAllRR();
        }
        return iRecruitmentRequestRepository.findRecruitmentRequestsByNameContaining(name);
    }

    @Override
    public Iterable<Object[]> statusFilter(String status) {
        return iRecruitmentRequestRepository.statusFilter(status);
    }

    @Override
    public Iterable<Object[]> getStatus() {
        return iRecruitmentRequestRepository.statusList();
    }
}
