package com.example.quanlydaotao.controller;
import com.example.quanlydaotao.dto.RecruitmentStatsDTO;
import com.example.quanlydaotao.service.RecruitmentStatsService;
import com.example.quanlydaotao.service.impl.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/recruitmentStats")
public class RecruitmentStatsController {
    @Autowired
    RecruitmentStatsService recruitmentStatsService;
    @Autowired
    private ExcelExportService excelExportService;

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

    @GetMapping("/maxRecruitmentWithYear")
    public ResponseEntity<RecruitmentStatsDTO> getMaxRecruitmentWithYear(){
        return new ResponseEntity<>(recruitmentStatsService.getMaxRecruitmentStatsWithYear(), HttpStatus.OK);
    }

    @GetMapping("/maxRecruitmentWithQuarter")
    public ResponseEntity<RecruitmentStatsDTO> getMaxRecruitmentWithQuarter(){
        return new ResponseEntity<>(recruitmentStatsService.getMaxRecruitmentStatsWithQuarter(), HttpStatus.OK);
    }

    @GetMapping("/maxRecruitmentWithMonth")
    public ResponseEntity<RecruitmentStatsDTO> getMaxRecruitmentWithMonth(){
        return new ResponseEntity<>(recruitmentStatsService.getMaxRecruitmentStatsWithMonth(), HttpStatus.OK);
    }

    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportRecruitmentStatsAll() {
        try {
            ByteArrayOutputStream outputStream = excelExportService.exportRecruitmentStatsToExcel();
            byte[] excelBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "recruitment_stats.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
