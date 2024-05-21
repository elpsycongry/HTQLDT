package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.service.TrainingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/trainingStats/month/{month}")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithMonth(@PathVariable int month) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithMonth(month);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/quarter/{quarter}")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithQuarter(@PathVariable int quarter) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithQuarter(quarter);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/year/{year}")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithYear(@PathVariable int year) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithYear(year);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }
}
