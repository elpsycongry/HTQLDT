package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.PlanFormDTO;
import com.example.quanlydaotao.model.RecruitmentPlan;
import com.example.quanlydaotao.model.RecruitmentPlanDetail;
import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.repository.IRecruitmentPlanDetailRepository;
import com.example.quanlydaotao.repository.IRecruitmentPlanRepository;
import com.example.quanlydaotao.service.IRecruitmentRequestService;
import com.example.quanlydaotao.service.impl.RecruitmentPlanDetailService;
import com.example.quanlydaotao.service.impl.RecruitmentPlanService;
import com.example.quanlydaotao.service.impl.RecruitmentRequestDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    private IRecruitmentPlanDetailRepository recruitmentPlanDetailRepository;
    @Autowired
    private IRecruitmentPlanRepository recruitmentPlanRepository;
    @Autowired
    private IRecruitmentRequestService recruitmentRequestService;

    @GetMapping("")
    public ResponseEntity<Page<RecruitmentPlan>> getRecruitmentPlan(@PageableDefault(5) Pageable pageable) {
        Page<RecruitmentPlan> recruitmentPlans = recruitmentPlanService.showRecruitmentPlan(pageable);
        return new ResponseEntity<>(recruitmentPlans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanFormDTO> getRecruitmentPlanById(@PathVariable("id") long id) {
        Optional<RecruitmentPlan> recruitmentPlan = recruitmentPlanService.showRecruitmentPlanById(id);
        Iterable<RecruitmentPlanDetail> recruitmentPlanDetails = recruitmentPlanDetailService.findByRecruitmentPlanId(id);
        List<RecruitmentPlanDetail> recruitmentPlanDetailsList = new ArrayList<>();
        for (RecruitmentPlanDetail recruitmentPlanDetail : recruitmentPlanDetails) {
            RecruitmentPlanDetail recruitmentPlanDetailNew = new RecruitmentPlanDetail();
            recruitmentPlanDetailNew.setId(recruitmentPlanDetail.getId());
            recruitmentPlanDetailNew.setType(recruitmentPlanDetail.getType());
            recruitmentPlanDetailNew.setNumberOfPersonnelNeeded(recruitmentPlanDetail.getNumberOfPersonnelNeeded());
            recruitmentPlanDetailNew.setNumberOfOutputPersonnel(recruitmentPlanDetail.getNumberOfOutputPersonnel());
            recruitmentPlanDetailsList.add(recruitmentPlanDetailNew);
        }
        PlanFormDTO planFormDTO = new PlanFormDTO();
        planFormDTO.setRecruitmentPlan(recruitmentPlan.get());
        planFormDTO.setPlanDetails(recruitmentPlanDetailsList);
        Optional<RecruitmentRequest> recruitmentRequest = recruitmentRequestService.findRecruitmentRequestById(recruitmentPlan.get().getRecruitmentRequest().getId());
        planFormDTO.setRecruitmentRequest(recruitmentRequest.get());
        planFormDTO.setIdUser(recruitmentPlan.get().getUsers().getId());
        return new ResponseEntity<>(planFormDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecruitmentPlan(@PathVariable("id") long id, @RequestBody PlanFormDTO planFormDTO) {
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
            recruitmentPlanService.updateRecruitmentPlan(planFormDTO,id);
        }catch (Exception e){
            return new ResponseEntity<>("Cập nhật dữ liệu thất bại", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Cập nhật dữ liệu thành công", HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createRecruitmentPlan(@RequestBody PlanFormDTO planFormDTO) {
        try {
            recruitmentPlanService.createRecruitmentPlan(planFormDTO);
        } catch (Exception e) {
            return new ResponseEntity<>("Thêm kế hoạch thất bại", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Thêm kế hoạch thành công", HttpStatus.OK);
    }
}
