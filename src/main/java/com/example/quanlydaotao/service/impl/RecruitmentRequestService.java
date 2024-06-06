package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.*;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import com.example.quanlydaotao.service.IRecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentRequestService implements IRecruitmentRequestService {
    @Autowired
    private IRecruitmentRequestRepository iRecruitmentRequestRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserRecruitmentActionService userActionService;
    @Autowired
    private RecruitmentRequestDetailService recruitmentRequestDetailService;
    @Autowired
    private UserRecruitmentActionService userRecruitmentActionService;


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

    @Override
    public String getRecruitmentNameById(long recruitmentId) {
        return iRecruitmentRequestRepository.findById(recruitmentId).map(RecruitmentRequest::getName).orElse(null);
    }

    public LocalDateTime getDateStart(long recruitmentId){
        return iRecruitmentRequestRepository.findById(recruitmentId).map(RecruitmentRequest::getDateStart).orElse(null);
    }
    public LocalDate getDateEnd(long recruitmentId){
        return iRecruitmentRequestRepository.findById(recruitmentId).map(RecruitmentRequest::getDateEnd).orElse(null);
    }

    public RecruitmentRequest createRecruitmentRequest(RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentRequest request = recruitmentFormDTO.getRecruitmentRequest();
        request.setStatus("Đã gửi");
        Optional<Users> users = usersService.findById(recruitmentFormDTO.getIdUser());
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
        System.out.println(request);
        createUserRecruitmentAction(recruitmentFormDTO.getIdUser(), request, UserAction.Demand.toString());

        List<RecruitmentRequestDetail> requestDetails = recruitmentFormDTO.getDetails();

        for (RecruitmentRequestDetail detail : requestDetails) {
            detail.setRecruitmentRequest(request);
            recruitmentRequestDetailService.saveDetail(detail);
        }
        return request;
    }


    public void deniedRequestRecruitment(long idRecruitment, long idUser, String status, String reason) {
        RecruitmentRequest recruitmentRequest = iRecruitmentRequestRepository.findById(idRecruitment).get();
        recruitmentRequest.setStatus(status)
            .setReason(reason);
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

        Iterable<RecruitmentRequestDetail> recruitmentRequestDetails = recruitmentRequestDetailService.findByRecruitmentRequestId(recruitmentFormDTO.getRecruitmentRequest().getId());
        Optional<UserRecruitmentAction> userRecruitmentAction = userRecruitmentActionService.findByRecruitmentRequestId(id);

        DeleteAllRecruitment(id, userRecruitmentAction);
        SaveRecruitment(recruitmentFormDTO, recruitmentRequestDetails);
    }

    private void SaveRecruitment(RecruitmentFormDTO recruitmentFormDTO, Iterable<RecruitmentRequestDetail> recruitmentRequestDetails) {
        RecruitmentRequest request = recruitmentFormDTO.getRecruitmentRequest();
        Optional<Users> users = usersService.findById(recruitmentFormDTO.getIdUser());
        request.setUsers(users.get());
        iRecruitmentRequestRepository.saveAndFlush(request);

        Users user = usersService.findById(recruitmentFormDTO.getIdUser()).get();

        UserRecruitmentAction userAction = new UserRecruitmentAction();
        userAction.setUser(user).setRecruitmentRequest(request)
                .setUser(user)
                .setAction(UserAction.Demand.toString());
        userRecruitmentActionService.save(userAction);

        List<RecruitmentRequestDetail> requestDetailsOld = new ArrayList<>();
        for (RecruitmentRequestDetail detail : recruitmentRequestDetails) {
            requestDetailsOld.add(detail);
        }

        List<RecruitmentRequestDetail> requestDetails = recruitmentFormDTO.getDetails();
        for (RecruitmentRequestDetail detail : requestDetails) {
            detail.setRecruitmentRequest(request);
            recruitmentRequestDetailService.saveDetail(detail);
        }

    }

    private void DeleteAllRecruitment(long id, Optional<UserRecruitmentAction> userRecruitmentAction) {
        userRecruitmentActionService.delete(userRecruitmentAction.get());
        recruitmentRequestDetailService.deleteAllByRecruitmentRequestId(id);
    }
    public Page<RecruitmentRequest> findByName(PaginateRequest paginateRequest, RecruitmentSearchDTO recruitmentSearchDTO) {
       return iRecruitmentRequestRepository.findAll(
               new RecruitmentSpec(recruitmentSearchDTO),
               PageRequest.of(
                       paginateRequest.getPage(),
                       paginateRequest.getSize(),
                       Sort.by(Sort.Direction.DESC, "id"))
       );
    }

    public Iterable<RecruitmentRequest> findAll() {
        return iRecruitmentRequestRepository.findAll();
    }

    public Optional<RecruitmentRequest> findById(Long id) {
        return iRecruitmentRequestRepository.findById(id);
    }

    public void activeByRecruitmentPlan(long idRecruitmentRequest) {
        RecruitmentRequest activedRequest = iRecruitmentRequestRepository.findById(idRecruitmentRequest).get()
                .setStatus("Đang tuyển dụng");
        iRecruitmentRequestRepository.save(activedRequest);
    }

    public void confimRequest(long idRecruitmentRequest) {
        RecruitmentRequest activedRequest = iRecruitmentRequestRepository.findById(idRecruitmentRequest).get()
                .setStatus("Đã xác nhận");
        iRecruitmentRequestRepository.save(activedRequest);
    }

    public ProcessDTO showProcessRequest(long idRecruitmentRequest) {
        ProcessDTO processDTO = new ProcessDTO();

        RecruitmentRequest request = iRecruitmentRequestRepository.findById(idRecruitmentRequest).get();
        processDTO.setRequestId(request.getId())
                .setRequestCreator(request.getUsers().getName() + "khởi tạo nhu cầu nhân sự:")
                .setRequestName(request.getName())
                .setStep(2);

        if (request.getStatus().equals("Đã gửi")) {
            processDTO.setStep(1)
                    .setDetAccept("");
        }

        if (request.getStatus().equals("Đã xác nhận") || request.getStatus().equals("Đang tuyển dụng")) {
            processDTO.setDetAccept("true");
        }

        String[] status = request.getStatus().split(" ");
        if (status[0].equals("Bị")) {
            processDTO.setDetAccept("false")
                .setReason(request.getReason())
                .setDetAccept("false")
                .setStep(1);
        }

    return processDTO;
    }
    
    public List<Long> getAllIdRequestsNeedSendEmail() {
        List<RecruitmentRequest> requests = iRecruitmentRequestRepository.findAll();
        List<Long> idRequestNeedSendEmail = new ArrayList<>();
        
        for (RecruitmentRequest request : requests) {
            String[] status = request.getStatus().split(" ");
            if (status[0].equals("Bị")) {
                continue;
            } else if (!isTrueRequestSend(request.getDateEnd())) {
                continue;
            }
            idRequestNeedSendEmail.add(request.getId());
        }
        return idRequestNeedSendEmail;
    }

    public static boolean isTrueRequestSend(LocalDate dateEnd) {
        boolean isTrue = true;

        LocalDate today = LocalDate.now();
        LocalDate sixDaysAgo = today.minusDays(6);

        if (dateEnd.isBefore(sixDaysAgo)) {
            isTrue = false;
        }
        return isTrue;
    }
}
