package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.*;
import com.example.quanlydaotao.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dashboard")
public class DashBoardController {
    @Autowired
    DashBoardService dashBoardService;

    @GetMapping("/personnelNeeds")
    public ResponseEntity<PersonnelNeedsDTO> getPersonnelNeeds(){
        return new ResponseEntity<>(dashBoardService.getPersonnelNeedsDTO(), HttpStatus.OK);
    }

    @GetMapping("/recruitmentPlan")
    public ResponseEntity<RecruitmentPlanDashBoardDTO> getRecruitmentPlan(){
        return new ResponseEntity<>(dashBoardService.getRecruitmentPlanDashBoardDTO(), HttpStatus.OK);
    }

    @GetMapping("/candidate")
    public ResponseEntity<CandidateDTO> getCandidate(){
        return new ResponseEntity<>(dashBoardService.getCandidateDTO(), HttpStatus.OK);
    }

    @GetMapping("/intern")
    public ResponseEntity<InternDashBoardDTO> getIntern(){
        return new ResponseEntity<>(dashBoardService.getInternDTO(), HttpStatus.OK);
    }
}
