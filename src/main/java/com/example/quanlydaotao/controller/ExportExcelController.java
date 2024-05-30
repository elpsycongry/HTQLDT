package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.service.TrainingStatsService;
import com.example.quanlydaotao.service.impl.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin("*")
public class ExportExcelController {
    @Autowired
    TrainingStatsService trainingStatsService;

    @GetMapping("/api/stats/export")
    public ResponseEntity<byte[]> exportTrainingStatsToExcel() {
        // Lấy dữ liệu thống kê đào tạo từ service
        TrainingStatsDTO trainingStats = trainingStatsService.getTrainingStats();

        try {
            ByteArrayOutputStream outputStream = ExcelExportService.exportTrainingStatsToExcel(trainingStats);
            byte[] excelBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "training_stats.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Xử lý ngoại lệ
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
