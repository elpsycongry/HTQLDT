package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.service.MailService;
import com.example.quanlydaotao.service.impl.InternService;
import com.example.quanlydaotao.service.impl.RecruitmentPlanDetailService;
import com.example.quanlydaotao.service.impl.RecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class TestRestController {
    @Autowired
    private MailService mailService;
    @Autowired
    private RecruitmentRequestService recruitmentRequestService;
    @Autowired
    private RecruitmentPlanDetailService recruitmentPlanDetailService;
    @Autowired
    private InternService internService;

    @GetMapping("/api/test/")
    public ResponseEntity<?> getAllIntern(@RequestParam(value = "id", required = false) Long id) {
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Subject");
        mailStructure.setText("Text");
        mailService.sendMailHtml("mimun0407@gmail.com", mailStructure);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/api/send/")
    public ResponseEntity get(){
       return new ResponseEntity<>(mailService.getDataSendEmail(),HttpStatus.OK);
    }
}
