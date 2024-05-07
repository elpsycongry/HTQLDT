package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.service.impl.RecruitmentRequestDetailService;
import com.example.quanlydaotao.service.impl.RecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
      return new ResponseEntity<>( recruitmentRequestIterable, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentRequest> getRecruitmentRequestById(@PathVariable long id) {
        Optional<RecruitmentRequest> recruitmentRequest = recruitmentRequestService.findRecruitmentRequestById(id);
        RecruitmentRequest recruitmentRequest1 = recruitmentRequest.get();
        UserRecruitmentAction userRecruitmentAction = new UserRecruitmentAction();
        userRecruitmentAction.setId(recruitmentRequest1.getDemandOriginator().getId());
        userRecruitmentAction.setUser(recruitmentRequest1.getDemandOriginator().getUser());
        recruitmentRequest1.setDemandOriginator(userRecruitmentAction);
        return new ResponseEntity<>(recruitmentRequest1, HttpStatus.OK);
    }
}
