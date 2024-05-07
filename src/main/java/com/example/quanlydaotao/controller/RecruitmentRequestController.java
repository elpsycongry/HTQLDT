package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.RecruitmentFormDTO;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import com.example.quanlydaotao.service.impl.RecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitmentRequests")
public class RecruitmentRequestController {
    @Autowired
    private RecruitmentRequestService recruitmentRequestService;

    @PostMapping
    public ResponseEntity createRecruitmentRequest(@RequestBody RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentFormDTO request = recruitmentFormDTO;
        recruitmentRequestService.createRecruitmentRequest(recruitmentFormDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
