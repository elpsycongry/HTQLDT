package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.service.TrainingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin("*")
public class StatsController {
    @Autowired
    private TrainingStatsService trainingStatsService;

    @GetMapping("/trainingStats/all")
    public ResponseEntity<TrainingStatsDTO> getTrainingStats() {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStats();
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/month")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithMonth() {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithMonth(5);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/quarter")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithQuarter() {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithQuarter(2);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/year")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithYear() {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithYear(2024);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }
}
