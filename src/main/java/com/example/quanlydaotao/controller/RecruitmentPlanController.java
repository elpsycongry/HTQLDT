package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.*;
import com.example.quanlydaotao.model.*;

import com.example.quanlydaotao.model.RecruitmentPlan;
import com.example.quanlydaotao.model.RecruitmentPlanDetail;
import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.repository.IRecruitmentPlanRepository;
import com.example.quanlydaotao.service.IRecruitmentRequestService;
import com.example.quanlydaotao.service.impl.RecruitmentPlanDetailService;
import com.example.quanlydaotao.service.impl.RecruitmentPlanService;
import com.example.quanlydaotao.service.impl.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans")
public class RecruitmentPlanController {
    @Autowired
    private RecruitmentPlanService recruitmentPlanService;
    @Autowired
    private RecruitmentPlanDetailService recruitmentPlanDetailService;
    @Autowired
    private IRecruitmentPlanRepository recruitmentPlanRepository;
    @Autowired
    private IRecruitmentRequestService recruitmentRequestService;
    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public ResponseEntity<Iterable<RecruitmentPlan>> getRecruitmentPlan() {
        Iterable<RecruitmentPlan> recruitmentPlans = recruitmentPlanRepository.findAll();
        return new ResponseEntity<>(recruitmentPlans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanFormDTO> getRecruitmentPlanById(@PathVariable("id") long id) {
        Optional<RecruitmentPlan> recruitmentPlan = recruitmentPlanService.showRecruitmentPlanById(id);
        Iterable<RecruitmentPlanDetail> recruitmentPlanDetails = recruitmentPlanDetailService.findByRecruitmentPlanId(id);
        List<RecruitmentPlanDetail> recruitmentPlanDetailsList = new ArrayList<>();
        for (RecruitmentPlanDetail recruitmentPlanDetail : recruitmentPlanDetails) {
            RecruitmentPlanDetail recruitmentPlanDetailNew = new RecruitmentPlanDetail();
            recruitmentPlanDetailNew.setId(recruitmentPlanDetail.getId())
                .setType(recruitmentPlanDetail.getType())
                .setNumberOfPersonnelNeeded(recruitmentPlanDetail.getNumberOfPersonnelNeeded())
                .setNumberOfOutputPersonnel(recruitmentPlanDetail.getNumberOfOutputPersonnel());
            recruitmentPlanDetailsList.add(recruitmentPlanDetailNew);
        }
        PlanFormDTO planFormDTO = new PlanFormDTO();
        planFormDTO.setRecruitmentPlan(recruitmentPlan.get())
            .setPlanDetails(recruitmentPlanDetailsList);
        Optional<RecruitmentRequest> recruitmentRequest = recruitmentRequestService.findRecruitmentRequestById(recruitmentPlan.get().getRecruitmentRequest().getId());
        planFormDTO.setRecruitmentRequest(recruitmentRequest.get())
            .setIdUser(recruitmentPlan.get().getUsers().getId());
        return new ResponseEntity<>(planFormDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecruitmentPlan(@PathVariable("id") long id,
                                                        @RequestBody PlanFormDTO planFormDTO) {
        Iterable<RecruitmentPlan> recruitmentPlans = recruitmentPlanRepository.findAll();
        try {
            for (RecruitmentPlan recruitmentPlan : recruitmentPlans) {
                if (planFormDTO.getRecruitmentPlan().getName() == recruitmentPlan.getName()) {
                    if (id != recruitmentPlan.getId()) {
                        return new ResponseEntity<>("Cập nhật dữ liệu thất bại", HttpStatus.BAD_REQUEST);
                    }
                }
            }
            planFormDTO.getRecruitmentPlan().setId(id);
            recruitmentPlanService.updateRecruitmentPlan(planFormDTO, id);
        } catch (Exception e) {
            return new ResponseEntity<>("Cập nhật dữ liệu thất bại", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Cập nhật dữ liệu thành công", HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createRecruitmentPlan(@RequestBody PlanFormDTO planFormDTO) {
        RecruitmentPlan recruitmentPlan;
        try {
             recruitmentPlan = recruitmentPlanService.createRecruitmentPlan(planFormDTO);
        } catch (Exception e) {
            return new ResponseEntity<>("Thêm kế hoạch thất bại", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(recruitmentPlan, HttpStatus.OK);
    }

    @PutMapping("/{planId}/users/{userId}")
    public ResponseEntity activeRecruitmentPlan(@PathVariable("planId") Long planId,
                                                @PathVariable("userId") long userId) {
        try {
            RecruitmentPlan recruitmentPlan = recruitmentPlanService.findById(planId).get();
            recruitmentPlan.setStatus("Đã xác nhận");

            Users users = usersService.findById(userId).get();

            UserPlanAction userPlanAction = new UserPlanAction(users, recruitmentPlan);
            userPlanAction.setAction(UserAction.Confirm.toString());

            recruitmentPlanService.activePlan(recruitmentPlan, userPlanAction);
        } catch (Exception e) {
            return new ResponseEntity("Thao tác thất bại", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Xác nhận thành công", HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity findAllByName(@RequestParam(value = "name",required = false) String name,
                                        @RequestParam(value = "status",required = false) String status,
                                        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        Page<RecruitmentPlan> recruitmentPlanPage = recruitmentPlanService.findAllByName(
                new PaginateRequest(page,size),
                new RecruitmentPlanDTO(name,status)
        );
        return new ResponseEntity<>(recruitmentPlanPage, HttpStatus.OK);
    }

    @PostMapping("/{id}/users/{idUser}")
    public ResponseEntity updateRecruitmentStatus(@RequestBody ReasonDTO reasonDTO,
                                                  @PathVariable("id") Long idPlan,
                                                  @PathVariable Long idUser) {
        String reason = reasonDTO.getReason();
        String action = "Bị từ chối bởi DECAN";
        try {
            recruitmentPlanService.DeniedRecruitmentPlan(idPlan, idUser, action, reason);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Đã từ chối", HttpStatus.OK);

    }
}
