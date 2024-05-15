package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.PlanFormDTO;
import com.example.quanlydaotao.dto.UserAction;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.*;
import com.example.quanlydaotao.service.IRecruitmentPlanService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private IRecruitmentPlanDetailRepository recruitmentPlanDetailRepository;
    @Autowired
    private IUserPlanActionRepository userPlanActionRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRecruitmentRequestRepository recruitmentRequestRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RecruitmentPlanDetailService recruitmentPlanDetailService;
    @Autowired
    private UserPlanActionService userPlanActionService;
    @Override
    public Page<RecruitmentPlan> showRecruitmentPlan(Pageable pageable) {
       return recruitmentPlanRepository.findAll(pageable);
    }

    @Override
    public Optional<RecruitmentPlan> showRecruitmentPlanById(long id) {
        return recruitmentPlanRepository.findById(id);
    }
    public void updateRecruitmentPlan(PlanFormDTO planFormDTO,long id) {
       Iterable<RecruitmentPlanDetail> recruitmentPlanDetails = recruitmentPlanDetailRepository.findByRecruitmentPlanId(planFormDTO.getRecruitmentPlan().getId());
       Optional<UserPlanAction> userPlanAction = userPlanActionRepository.findByRecruitmentPlanId(id);
        deleteAllRecruitmentPlan(id,userPlanAction);
        SaveRecruitmentPlan(planFormDTO, recruitmentPlanDetails);
    }

    private void SaveRecruitmentPlan(PlanFormDTO planFormDTO, Iterable<RecruitmentPlanDetail> recruitmentPlanDetails) {
        RecruitmentPlan recruitmentPlan = planFormDTO.getRecruitmentPlan();
        Optional<Users> usersOptional = userRepository.findById(planFormDTO.getIdUser());
        recruitmentPlan.setUsers(usersOptional.get());
        recruitmentPlanRepository.saveAndFlush(recruitmentPlan);
        UserPlanAction userPlanActionNew = new UserPlanAction();
        userPlanActionNew.setUser(usersOptional.get());
        userPlanActionNew.setRecruitmentPlan(recruitmentPlan);
        userPlanActionNew.setAction(UserAction.Plane.toString());
        userPlanActionRepository.save(userPlanActionNew);
        List<RecruitmentPlanDetail> recruitmentPlanDetailsList = new ArrayList<>();
        for (RecruitmentPlanDetail recruitmentPlanDetail : recruitmentPlanDetails){
            recruitmentPlanDetailsList.add(recruitmentPlanDetail);
        }
        List<RecruitmentPlanDetail> recruitmentPlanDetailsListNew = planFormDTO.getPlanDetails();
        for (RecruitmentPlanDetail recruitmentPlanDetail : recruitmentPlanDetailsListNew) {
            recruitmentPlanDetail.setRecruitmentPlan(recruitmentPlan);
            recruitmentPlanDetailRepository.save(recruitmentPlanDetail);
        }
        Optional<RecruitmentRequest> recruitmentRequest = recruitmentRequestRepository.findById(recruitmentPlan.getRecruitmentRequest().getId());
    }

    public void deleteAllRecruitmentPlan(long id , Optional<UserPlanAction> userPlanAction) {
        userPlanActionRepository.deleteById(userPlanAction.get().getId());
        recruitmentPlanDetailRepository.deleteAllByRecruitmentPlanId(id);
    }

    public void createRecruitmentPlan(PlanFormDTO planFormDTO) {
        Users users = usersService.findById(planFormDTO.getIdUser()).get();
        RecruitmentPlan recruitmentPlan = planFormDTO.getRecruitmentPlan();
        List<RecruitmentPlanDetail> recruitmentPlanDetails = planFormDTO.getPlanDetails();

        recruitmentPlan.setUsers(users)
                .setStatus("Đã gửi");
        LocalDateTime localDateTime = LocalDateTime.now();
        int day = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
        recruitmentPlan.setDateCreatePlan(dateTime);
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
    }
}
