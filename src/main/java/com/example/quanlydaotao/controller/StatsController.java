package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.AverageOfSubjectDTO;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin("*")
public class StatsController {
    @Autowired
    private TrainingStatsService trainingStatsService;
    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/trainingStats/all")
    public ResponseEntity<TrainingStatsDTO> getTrainingStats() {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStats();
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/month")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithMonth(@RequestParam(name = "month") int month,
                                                                      @RequestParam(name = "year") int year) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithMonth(month, year);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/quarter")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithQuarter(@RequestParam(name = "quarter") int quarter,
                                                                        @RequestParam(name = "year") int year) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithQuarter(quarter, year);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingStats/year")
    public ResponseEntity<TrainingStatsDTO> getTrainingStatsWithYear(@RequestParam(name = "year") int year) {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStatsWithYear(year);
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }

    @GetMapping("/trainingCharts/year")
    public ResponseEntity<List<TrainingStatsDTO>> getTrainingCharts(@RequestParam(name = "year") int year) {
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

    @GetMapping("/getGrowthStatisticsWithYear")
    public ResponseEntity<TrainingStatsDTO> getAverageOfSubject(@RequestParam(name = "year") int year) {
        return new ResponseEntity<>(trainingStatsService.getTrainingStatsWithYear(year), HttpStatus.OK);
    }

    @GetMapping("/getGrowthStatisticsWithMonth")
    public ResponseEntity<TrainingStatsDTO> getGrowthStatisticsWithMonth(@RequestParam(name = "month") int month,
                                                                         @RequestParam(name = "year") int year) {
        return new ResponseEntity<>(trainingStatsService.getTrainingStatsWithMonth(month, year), HttpStatus.OK);
    }

    @GetMapping("/getGrowthStatisticsWithQuarter")
    public ResponseEntity<TrainingStatsDTO> getGrowthStatisticsWithQuarter(@RequestParam(name = "quarter") int quarter,
                                                                           @RequestParam(name = "year") int year) {
        return new ResponseEntity<>(trainingStatsService.getTrainingStatsWithQuarter(quarter, year), HttpStatus.OK);
    }

    @GetMapping("/getMaxGrowthStatisticsWithYear")
    public ResponseEntity<TrainingStatsDTO> getMaxAverageOfSubject() {
        return new ResponseEntity<>(trainingStatsService.getMaxTrainingStatsWithYear(), HttpStatus.OK);
    }

    @GetMapping("/getMaxGrowthStatisticsWithMonth")
    public ResponseEntity<TrainingStatsDTO> getMaxAverageOfMonth() {
        return new ResponseEntity<>(trainingStatsService.getMaxTrainingStatsWithMonth(), HttpStatus.OK);
    }

    @GetMapping("/getMaxGrowthStatisticsWithQuarter")
    public ResponseEntity<TrainingStatsDTO> getMaxAverageOfQuarter() {
        return new ResponseEntity<>(trainingStatsService.getMaxTrainingStatsWithQuarter(), HttpStatus.OK);
    }

    @GetMapping("/exportExcelTrainingStatsAll")
    public ResponseEntity<byte[]> exportTrainingStatsAll() {
        try {
            ByteArrayOutputStream outputStream = excelExportService.exportTrainingStatsToExcel();
            byte[] excelBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "training_stats.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exportExcelTrainingStats/monthExportExcel")
    public ResponseEntity<byte[]> exportTrainingStatsByMonth(@RequestParam int month, @RequestParam int year) {
        try {
            ByteArrayOutputStream outputStream = excelExportService.exportTrainingStatsToExcelByMonth(month, year);
            byte[] excelBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "training_stats_month.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exportExcelTrainingStats/quarterExportExcel")
    public ResponseEntity<byte[]> exportTrainingStatsByQuarter(@RequestParam int quarter, @RequestParam int year) {
        try {
            ByteArrayOutputStream outputStream = excelExportService.exportTrainingStatsToExcelByQuarter(quarter, year);
            byte[] excelBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "training_stats_quarter.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exportExcelTrainingStats/year")
    public ResponseEntity<byte[]> exportTrainingStatsByYear(@RequestParam int year) {
        try {
            ByteArrayOutputStream outputStream = excelExportService.exportTrainingStatsToExcelByYear(year);
            byte[] excelBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "training_stats_year.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
