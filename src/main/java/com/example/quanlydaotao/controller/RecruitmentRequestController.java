package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.RecruitmentPlanDetail;
import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.service.impl.RecruitmentRequestDetailService;

import com.example.quanlydaotao.dto.RecruitmentFormDTO;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;

import com.example.quanlydaotao.service.impl.RecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/recruitmentRequest")
@CrossOrigin(value = "*")
public class RecruitmentRequestController {
    @Autowired
    private RecruitmentRequestService recruitmentRequestService;
    @Autowired
    private RecruitmentRequestDetailService recruitmentRequestDetailService;
    @GetMapping()
    public ResponseEntity<Iterable<RecruitmentRequest>> getAllRecruitmentRequest() {
        Iterable<RecruitmentRequest> recruitmentRequestIterable = recruitmentRequestService.getAllRecruitmentRequests();
        return new ResponseEntity<>(recruitmentRequestIterable, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentRequest> getRecruitmentRequestById(@PathVariable long id) {
        Optional<RecruitmentRequest> recruitmentRequest = recruitmentRequestService.findRecruitmentRequestById(id);
        RecruitmentRequest recruitmentRequest1 = recruitmentRequest.get();
        return new ResponseEntity<>(recruitmentRequest1, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<Iterable<RecruitmentRequestDetail>> getRecruitmentRequestDetail(@PathVariable long id) {
        Iterable<RecruitmentRequestDetail> recruitmentRequestDetail = recruitmentRequestDetailService.findByRecruitmentId(id);
        return new ResponseEntity<>(recruitmentRequestDetail, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity createRecruitmentRequest(@RequestBody RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentFormDTO request = recruitmentFormDTO;
        recruitmentRequestService.createRecruitmentRequest(recruitmentFormDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
