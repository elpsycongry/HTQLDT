package com.example.quanlydaotao.service.impl;
import com.example.quanlydaotao.dto.TrainingStatsDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExcelExportService {
    public static ByteArrayOutputStream exportTrainingStatsToExcel(TrainingStatsDTO trainingStats) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Training Stats");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tiêu chí");
        headerRow.createCell(1).setCellValue("Giá trị");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("Thực tập sinh nhập học");
        dataRow.createCell(1).setCellValue(trainingStats.getInternsEnrolled());

        // Tiếp tục tạo các hàng dữ liệu cho các thuộc tính khác của TrainingStatsDTO

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }
}