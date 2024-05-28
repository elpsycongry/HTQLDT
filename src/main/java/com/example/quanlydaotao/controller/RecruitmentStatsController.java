package com.example.quanlydaotao.controller;
import com.example.quanlydaotao.dto.RecruitmentStatsDTO;
import com.example.quanlydaotao.service.RecruitmentStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/recruitmentChart/year")
    public ResponseEntity<List<RecruitmentStatsDTO>> getRecruitmentChartYear(@RequestParam int year) {
        List<RecruitmentStatsDTO> recruitmentStatsDTOList = new ArrayList<>();

        RecruitmentStatsDTO recruitmentStatsDTO = recruitmentStatsService.getRecruitmentStatsByMonth(1, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO);

        RecruitmentStatsDTO recruitmentStatsDTO1 = recruitmentStatsService.getRecruitmentStatsByMonth(2, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO1);

        RecruitmentStatsDTO recruitmentStatsDTO2 = recruitmentStatsService.getRecruitmentStatsByMonth(3, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO2);

        RecruitmentStatsDTO recruitmentStatsDTO3 = recruitmentStatsService.getRecruitmentStatsByMonth(4, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO3);

        RecruitmentStatsDTO recruitmentStatsDTO4 = recruitmentStatsService.getRecruitmentStatsByMonth(5, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO4);

        RecruitmentStatsDTO recruitmentStatsDTO5 = recruitmentStatsService.getRecruitmentStatsByMonth(6, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO5);

        RecruitmentStatsDTO recruitmentStatsDTO6 = recruitmentStatsService.getRecruitmentStatsByMonth(7, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO6);

        RecruitmentStatsDTO recruitmentStatsDTO7 = recruitmentStatsService.getRecruitmentStatsByMonth(8, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO7);

        RecruitmentStatsDTO recruitmentStatsDTO8 = recruitmentStatsService.getRecruitmentStatsByMonth(9, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO8);

        RecruitmentStatsDTO recruitmentStatsDTO9 = recruitmentStatsService.getRecruitmentStatsByMonth(10, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO9);

        RecruitmentStatsDTO recruitmentStatsDTO10 = recruitmentStatsService.getRecruitmentStatsByMonth(11, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO10);

        RecruitmentStatsDTO recruitmentStatsDTO11 = recruitmentStatsService.getRecruitmentStatsByMonth(12, year);
        recruitmentStatsDTOList.add(recruitmentStatsDTO11);

        return new ResponseEntity<>(recruitmentStatsDTOList, HttpStatus.OK);
    }
}
