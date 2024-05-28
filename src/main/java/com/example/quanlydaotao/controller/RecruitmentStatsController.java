package com.example.quanlydaotao.controller;
import com.example.quanlydaotao.dto.RecruitmentStatsDTO;
import com.example.quanlydaotao.service.RecruitmentStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/recruitmentStats")
public class RecruitmentStatsController {
    @Autowired
    RecruitmentStatsService recruitmentStatsService;

    @GetMapping
    public ResponseEntity<RecruitmentStatsDTO> getRecruitmentStats() {
        return new ResponseEntity<>(recruitmentStatsService.getAllRecruitmentStats(), HttpStatus.OK);
    }
}
