package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.RecruitmentFormDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/api/recruitmentRequests")
public class RecruitmentRequestController {
    public EntityResponse createRecruitmentRequest(@RequestBody RecruitmentFormDTO recruitmentFormDTO) {


    }

}
