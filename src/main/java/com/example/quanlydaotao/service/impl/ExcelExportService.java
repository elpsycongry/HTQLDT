package com.example.quanlydaotao.service.impl;
import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.service.TrainingStatsService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ExcelExportService {

    public static ByteArrayOutputStream exportTrainingStatsToExcel(TrainingStatsDTO trainingStats) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tất cả hệ thống");

        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("Tiêu chí");
        headerRow.createCell(1).setCellValue("Giá trị");

        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("Thực tập sinh nhập học");
        dataRow1.createCell(1).setCellValue(trainingStats.getInternsEnrolled());
        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("Thực tập sinh tot nghiep");
        dataRow2.createCell(1).setCellValue(trainingStats.getGraduatingInterns());
        Row dataRow3 = sheet.createRow(3);
        dataRow3.createCell(0).setCellValue("Thực tập sinh fail");
        dataRow3.createCell(1).setCellValue(trainingStats.getInternsFailed());
        Row dataRow4 = sheet.createRow(4);
        dataRow4.createCell(0).setCellValue("Ty le pass/ fail");
        dataRow4.createCell(1).setCellValue(trainingStats.getRate());
        Row dataRow5 = sheet.createRow(5);
        dataRow5.createCell(0).setCellValue("Thực tập sinh dang thuc tap");
        dataRow5.createCell(1).setCellValue(trainingStats.getInternsCurrentlyPracticing());
        Row dataRow6 = sheet.createRow(6);
        dataRow6.createCell(0).setCellValue("Thực tập sinh nghi thuc tap");
        dataRow6.createCell(1).setCellValue(trainingStats.getInternsQuitInternship());
        Row dataRow7 = sheet.createRow(7);
        dataRow7.createCell(0).setCellValue("Diem tot nghiep trung binh");
        dataRow7.createCell(1).setCellValue(trainingStats.getAverageGraduationScore());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }
}