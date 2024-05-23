package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.TraingChartsDTO;
import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.service.TrainingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin("*")
public class  StatsController {
    @Autowired
    private TrainingStatsService trainingStatsService;

    @GetMapping("/trainingStats/all")
    public ResponseEntity<TrainingStatsDTO> getTrainingStats() {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStats();
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/month")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithMonth(@RequestParam(name = "month")int month,
                                                                      @RequestParam(name = "year")int year) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithMonth(month, year);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/quarter")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithQuarter(@RequestParam(name = "quarter")int quarter,
                                                                        @RequestParam(name = "year")int year) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithQuarter(quarter, year);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/year")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithYear(@RequestParam(name = "year")int year) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithYear(year);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingCharts/year")
    public ResponseEntity<List<TrainingStatsDTO>> getTrainingCharts(@RequestParam(name = "year")int year) {
        List<TrainingStatsDTO> traingChartsDTOList = new ArrayList<>();
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithMonth(1, year);
        traingChartsDTOList.add(trainingStatsDTO);

        TrainingStatsDTO trainingStatsDTO1 = trainingStatsService.getTrainingStatsWithMonth(2, year);
        traingChartsDTOList.add(trainingStatsDTO1);

        TrainingStatsDTO trainingStatsDTO2 = trainingStatsService.getTrainingStatsWithMonth(3, year);

        traingChartsDTOList.add(trainingStatsDTO2);
        TrainingStatsDTO trainingStatsDTO3 = trainingStatsService.getTrainingStatsWithMonth(4, year);

        traingChartsDTOList.add(trainingStatsDTO3);
        TrainingStatsDTO trainingStatsDTO4 = trainingStatsService.getTrainingStatsWithMonth(5, year);

        traingChartsDTOList.add(trainingStatsDTO4);
        TrainingStatsDTO trainingStatsDTO5 = trainingStatsService.getTrainingStatsWithMonth(6, year);

        traingChartsDTOList.add(trainingStatsDTO5);
        TrainingStatsDTO trainingStatsDTO6 = trainingStatsService.getTrainingStatsWithMonth(7, year);

        traingChartsDTOList.add(trainingStatsDTO6);
        TrainingStatsDTO trainingStatsDTO7 = trainingStatsService.getTrainingStatsWithMonth(8, year);

        traingChartsDTOList.add(trainingStatsDTO7);
        TrainingStatsDTO trainingStatsDTO8 = trainingStatsService.getTrainingStatsWithMonth(9, year);

        traingChartsDTOList.add(trainingStatsDTO8);
        TrainingStatsDTO trainingStatsDTO9 = trainingStatsService.getTrainingStatsWithMonth(10, year);

        traingChartsDTOList.add(trainingStatsDTO9);
        TrainingStatsDTO trainingStatsDTO10 = trainingStatsService.getTrainingStatsWithMonth(11, year);

        traingChartsDTOList.add(trainingStatsDTO10);
        TrainingStatsDTO trainingStatsDTO11 = trainingStatsService.getTrainingStatsWithMonth(12, year);
        traingChartsDTOList.add(trainingStatsDTO11);

        return new ResponseEntity<>(traingChartsDTOList, HttpStatus.OK);
    }
}
