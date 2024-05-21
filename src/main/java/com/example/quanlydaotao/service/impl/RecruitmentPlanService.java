package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.*;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.*;
import com.example.quanlydaotao.service.IRecruitmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentPlanService implements IRecruitmentPlanService {
    @Autowired
    private IRecruitmentPlanRepository recruitmentPlanRepository;
    @Autowired
    private RecruitmentRequestService recruitmentRequestService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RecruitmentPlanDetailService recruitmentPlanDetailService;
    @Autowired
    private UserPlanActionService userPlanActionService;
    @Override
    public Page<RecruitmentPlan> showRecruitmentPlan(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable reversedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return recruitmentPlanRepository.findAll(reversedPageable);
    }

    @Override
    public Optional<RecruitmentPlan> showRecruitmentPlanById(long id) {
        return recruitmentPlanRepository.findById(id);
    }


    public void updateRecruitmentPlan(PlanFormDTO planFormDTO,long id) {
       Iterable<RecruitmentPlanDetail> recruitmentPlanDetails = recruitmentPlanDetailService.findByRecruitmentPlanId(planFormDTO.getRecruitmentPlan().getId());
       Optional<UserPlanAction> userPlanAction = userPlanActionService.findByRecruitmentPlanId(id);
        deleteAllRecruitmentPlan(id,userPlanAction);
        SaveRecruitmentPlan(planFormDTO, recruitmentPlanDetails);
    }

    private void SaveRecruitmentPlan(PlanFormDTO planFormDTO, Iterable<RecruitmentPlanDetail> recruitmentPlanDetails) {
        RecruitmentPlan recruitmentPlan = planFormDTO.getRecruitmentPlan();
        Optional<Users> usersOptional = usersService.findById(planFormDTO.getIdUser());
        recruitmentPlan.setUsers(usersOptional.get());

        recruitmentPlanRepository.saveAndFlush(recruitmentPlan);

        UserPlanAction userPlanActionNew = new UserPlanAction();
        userPlanActionNew.setUser(usersOptional.get())
            .setRecruitmentPlan(recruitmentPlan)
            .setAction(UserAction.Plane.toString());
        userPlanActionService.save(userPlanActionNew);

        List<RecruitmentPlanDetail> recruitmentPlanDetailsList = new ArrayList<>();
        for (RecruitmentPlanDetail recruitmentPlanDetail : recruitmentPlanDetails){
            recruitmentPlanDetailsList.add(recruitmentPlanDetail);
        }

        List<RecruitmentPlanDetail> recruitmentPlanDetailsListNew = planFormDTO.getPlanDetails();
        for (RecruitmentPlanDetail recruitmentPlanDetail : recruitmentPlanDetailsListNew) {
            recruitmentPlanDetail.setRecruitmentPlan(recruitmentPlan);
            recruitmentPlanDetailService.save(recruitmentPlanDetail);
        }

        Optional<RecruitmentRequest> recruitmentRequest = recruitmentRequestService.findById(recruitmentPlan.getRecruitmentRequest().getId());
    }

    public void deleteAllRecruitmentPlan(long id , Optional<UserPlanAction> userPlanAction) {
        userPlanActionService.deleteById(userPlanAction.get().getId());
        recruitmentPlanDetailService.deleteAllByRecruitmentPlanId(id);
    }

    public void createRecruitmentPlan(PlanFormDTO planFormDTO) throws Exception {
        Users users = usersService.findById(planFormDTO.getIdUser()).get();
        RecruitmentPlan recruitmentPlan = planFormDTO.getRecruitmentPlan();
        List<RecruitmentPlanDetail> recruitmentPlanDetails = planFormDTO.getPlanDetails();
        LocalDateTime localDateTime = LocalDateTime.now();
        int day = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);

        recruitmentPlan.setUsers(users)
                .setStatus("Đã gửi")
                .setDateCreatePlan(dateTime);

        recruitmentPlan = recruitmentPlanRepository.save(recruitmentPlan);

        for (RecruitmentPlanDetail recruitmentPlanDetail : recruitmentPlanDetails) {
            recruitmentPlanDetail.setRecruitmentPlan(recruitmentPlan);
            recruitmentPlanDetailService.save(recruitmentPlanDetail);
        }

        UserPlanAction userPlanAction = new UserPlanAction();
        userPlanAction.setRecruitmentPlan(recruitmentPlan)
                .setUser(users)
                .setAction(UserAction.Plane.toString());

        userPlanActionService.save(userPlanAction);

        recruitmentRequestService.confimRequest(recruitmentPlan.getRecruitmentRequest().getId());
    }

    public Optional<RecruitmentPlan> findById(Long id) {
        return recruitmentPlanRepository.findById(id);
    }

    public void activePlan(RecruitmentPlan recruitmentPlan, UserPlanAction userPlanAction) throws Exception {
        recruitmentPlanRepository.save(recruitmentPlan);
        userPlanActionService.save(userPlanAction);

        recruitmentRequestService.activeByRecruitmentPlan(recruitmentPlan.getRecruitmentRequest().getId());
    }
    @Override
    public Page<RecruitmentPlan> findAllByName(PaginateRequest paginateRequest, RecruitmentPlanDTO recruitmentPlanDTO) {
        return recruitmentPlanRepository.findAll(
                new RecruitmentPlanSpec(recruitmentPlanDTO),
                PageRequest.of(
                        paginateRequest.getPage(),
                        paginateRequest.getSize(),
                        Sort.by(Sort.Direction.DESC, "id")
                )
        );
    }

    public void DeniedRecruitmentPlan(long idPlan, long idUser, String status, String reason) {
        RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(idPlan).get();
        recruitmentPlan.setStatus(status)
            .setReason(reason);
        recruitmentPlan = recruitmentPlanRepository.save(recruitmentPlan);
        String action = UserAction.Denied.toString();
        createUserPlanAction(idUser, recruitmentPlan, action);

        long idRecruitmentRequest = recruitmentPlan.getRecruitmentRequest().getId();
        recruitmentRequestService.deniedRequestRecruitment(idRecruitmentRequest, idUser, status, reason);
    }

    private void createUserPlanAction(long idUser, RecruitmentPlan recruitmentPlan, String action) {
        Users user = usersService.findById(idUser).get();
        UserPlanAction userAction = new UserPlanAction();
        userAction.setUser(user)
                .setRecruitmentPlan(recruitmentPlan)
                .setUser(user)
                .setAction(action);
        userPlanActionService.save(userAction);
    }
}
