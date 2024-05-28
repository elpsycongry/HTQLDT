package com.example.quanlydaotao.controller;
import com.example.quanlydaotao.dto.RecruitmentStatsDTO;
import com.example.quanlydaotao.service.RecruitmentStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/recruitmentStats")
public class RecruitmentStatsController {
    @Autowired
    RecruitmentStatsService recruitmentStatsService;

    @GetMapping("/month")
    public ResponseEntity<RecruitmentStatsDTO> getRecruitmentStatsWithMonth(@RequestParam int month){
        return new ResponseEntity<>(recruitmentStatsService.getRecruitmentStatsByMonth(month, 2024), HttpStatus.OK);
    }

    @GetMapping("/quarter")
    public ResponseEntity<RecruitmentStatsDTO> getRecruitmentStatsWithQuarter(@RequestParam int quarter){
        return new ResponseEntity<>(recruitmentStatsService.getRecruitmentStatsByQuarter(quarter, 2024), HttpStatus.OK);
    }

    @GetMapping("/year")
    public ResponseEntity<RecruitmentStatsDTO> getRecruitmentStatsWithYear(@RequestParam int year){
        return new ResponseEntity<>(recruitmentStatsService.getRecruitmentStatsByYear(year), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<RecruitmentStatsDTO> getRecruitmentStats() {
        return new ResponseEntity<>(recruitmentStatsService.getAllRecruitmentStats(), HttpStatus.OK);
    }
}
