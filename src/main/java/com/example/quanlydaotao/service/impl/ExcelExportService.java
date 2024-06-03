package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.AverageOfSubjectDTO;
import com.example.quanlydaotao.dto.RecruitmentStatsDTO;
import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.service.RecruitmentStatsService;
import com.example.quanlydaotao.service.TrainingStatsService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private TrainingStatsService trainingStatsService;
    @Autowired
    private RecruitmentStatsService recruitmentStatsService;

    public ByteArrayOutputStream exportTrainingStatsToExcel() throws IOException {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        int currentQuarter = (currentMonth - 1) / 3 + 1;

        TrainingStatsDTO trainingStats = trainingStatsService.getTrainingStats();
        TrainingStatsDTO trainingStatsWithMonth = trainingStatsService.getTrainingStatsWithMonth(currentMonth, currentYear);
        TrainingStatsDTO trainingStatsWithQuarter = trainingStatsService.getTrainingStatsWithQuarter(currentQuarter, currentYear);
        TrainingStatsDTO trainingStatsWithYear = trainingStatsService.getTrainingStatsWithYear(currentYear);
        List<AverageOfSubjectDTO> allAverageOfSubjects = trainingStatsService.getAllAverageOfSubject();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tất cả hệ thống");

        // Create font for headers
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14); // Set font size to 14

        // Create style for headers
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Tiêu chí");
        headerRow.createCell(2).setCellValue("Giá trị");
        headerRow.getCell(0).setCellStyle(headerCellStyle);
        headerRow.getCell(1).setCellStyle(headerCellStyle);
        headerRow.getCell(2).setCellStyle(headerCellStyle);

        // Create style for data
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Populate "Tất cả hệ thống" sheet
        createDataRow(sheet, 1, 1, "Thực tập sinh nhập học", trainingStats.getInternsEnrolled(), dataCellStyle);
        createDataRow(sheet, 2, 2, "Thực tập sinh tốt nghiệp", trainingStats.getGraduatingInterns(), dataCellStyle);
        createDataRow(sheet, 3, 3, "Thực tập sinh fail", trainingStats.getInternsFailed(), dataCellStyle);
        createDataRow(sheet, 4, 4, "Tỷ lệ pass/ fail", trainingStats.getRate(), dataCellStyle);
        createDataRow(sheet, 5, 5, "Thực tập sinh đang thực tập", trainingStats.getInternsCurrentlyPracticing(), dataCellStyle);
        createDataRow(sheet, 6, 6, "Thực tập sinh nghỉ thực tập", trainingStats.getInternsQuitInternship(), dataCellStyle);
        createDataRow(sheet, 7, 7, "Điểm tốt nghiệp trung bình", trainingStats.getAverageGraduationScore(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(sheet, 200);

        // Populate "Tháng" sheet
        Sheet monthSheet = workbook.createSheet("Tháng");
        createHeaderRow(monthSheet, headerCellStyle);
        createDataRow(monthSheet, 1, 1, "Thực tập sinh nhập học", trainingStatsWithMonth.getInternsEnrolled(), dataCellStyle);
        createDataRow(monthSheet, 2, 2, "Thực tập sinh tốt nghiệp", trainingStatsWithMonth.getGraduatingInterns(), dataCellStyle);
        createDataRow(monthSheet, 3, 3, "Thực tập sinh fail", trainingStatsWithMonth.getInternsFailed(), dataCellStyle);
        createDataRow(monthSheet, 4, 4, "Tỷ lệ pass/ fail", trainingStatsWithMonth.getRate(), dataCellStyle);
        createDataRow(monthSheet, 5, 5, "Thực tập sinh đang thực tập", trainingStatsWithMonth.getInternsCurrentlyPracticing(), dataCellStyle);
        createDataRow(monthSheet, 6, 6, "Thực tập sinh nghỉ thực tập", trainingStatsWithMonth.getInternsQuitInternship(), dataCellStyle);
        createDataRow(monthSheet, 7, 7, "Điểm tốt nghiệp trung bình", trainingStatsWithMonth.getAverageGraduationScore(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(monthSheet, 200);

        // Populate "Quý" sheet
        Sheet quarterSheet = workbook.createSheet("Quý");
        createHeaderRow(quarterSheet, headerCellStyle);
        createDataRow(quarterSheet, 1, 1, "Thực tập sinh nhập học", trainingStatsWithQuarter.getInternsEnrolled(), dataCellStyle);
        createDataRow(quarterSheet, 2, 2, "Thực tập sinh tốt nghiệp", trainingStatsWithQuarter.getGraduatingInterns(), dataCellStyle);
        createDataRow(quarterSheet, 3, 3, "Thực tập sinh fail", trainingStatsWithQuarter.getInternsFailed(), dataCellStyle);
        createDataRow(quarterSheet, 4, 4, "Tỷ lệ pass/ fail", trainingStatsWithQuarter.getRate(), dataCellStyle);
        createDataRow(quarterSheet, 5, 5, "Thực tập sinh đang thực tập", trainingStatsWithQuarter.getInternsCurrentlyPracticing(), dataCellStyle);
        createDataRow(quarterSheet, 6, 6, "Thực tập sinh nghỉ thực tập", trainingStatsWithQuarter.getInternsQuitInternship(), dataCellStyle);
        createDataRow(quarterSheet, 7, 7, "Điểm tốt nghiệp trung bình", trainingStatsWithQuarter.getAverageGraduationScore(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(quarterSheet, 200);

        // Populate "Năm" sheet
        Sheet yearSheet = workbook.createSheet("Năm");
        createHeaderRow(yearSheet, headerCellStyle);
        createDataRow(yearSheet, 1, 1, "Thực tập sinh nhập học", trainingStatsWithYear.getInternsEnrolled(), dataCellStyle);
        createDataRow(yearSheet, 2, 2, "Thực tập sinh tốt nghiệp", trainingStatsWithYear.getGraduatingInterns(), dataCellStyle);
        createDataRow(yearSheet, 3, 3, "Thực tập sinh fail", trainingStatsWithYear.getInternsFailed(), dataCellStyle);
        createDataRow(yearSheet, 4, 4, "Tỷ lệ pass/ fail", trainingStatsWithYear.getRate(), dataCellStyle);
        createDataRow(yearSheet, 5, 5, "Thực tập sinh đang thực tập", trainingStatsWithYear.getInternsCurrentlyPracticing(), dataCellStyle);
        createDataRow(yearSheet, 6, 6, "Thực tập sinh nghỉ thực tập", trainingStatsWithYear.getInternsQuitInternship(), dataCellStyle);
        createDataRow(yearSheet, 7, 7, "Điểm tốt nghiệp trung bình", trainingStatsWithYear.getAverageGraduationScore(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(yearSheet, 200);

        // Populate "Các môn học" sheet
        Sheet subjectSheet = workbook.createSheet("Các môn học");
        createSubjectHeaderRow(subjectSheet, headerCellStyle);

        int rowNum = 1;
        for (AverageOfSubjectDTO subject : allAverageOfSubjects) {
            createSubjectDataRow(subjectSheet, rowNum++, subject, dataCellStyle);
        }

        // Set column widths to 200 pixels
        setColumnWidths(subjectSheet, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }

    public ByteArrayOutputStream exportTrainingStatsToExcelByMonth(int month, int year) throws IOException {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        TrainingStatsDTO trainingStatsWithMonth = trainingStatsService.getTrainingStatsWithMonth(month, currentYear);
        return createExcel(trainingStatsWithMonth, "Tháng");
    }

    public ByteArrayOutputStream exportTrainingStatsToExcelByQuarter(int quarter, int year) throws IOException {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        TrainingStatsDTO trainingStatsWithQuarter = trainingStatsService.getTrainingStatsWithQuarter(quarter, currentYear);
        return createExcel(trainingStatsWithQuarter, "Quý");
    }

    public ByteArrayOutputStream exportTrainingStatsToExcelByYear(int year) throws IOException {
        TrainingStatsDTO trainingStatsWithYear = trainingStatsService.getTrainingStatsWithYear(year);
        return createExcel(trainingStatsWithYear, "Năm");
    }

    private ByteArrayOutputStream createExcel(TrainingStatsDTO trainingStats, String sheetName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // Create font for headers
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14); // Set font size to 14

        // Create style for headers
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Tiêu chí");
        headerRow.createCell(2).setCellValue("Giá trị");
        headerRow.getCell(0).setCellStyle(headerCellStyle);
        headerRow.getCell(1).setCellStyle(headerCellStyle);
        headerRow.getCell(2).setCellStyle(headerCellStyle);

        // Create style for data
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Populate the sheet
        createDataRow(sheet, 1, 1, "Thực tập sinh nhập học", trainingStats.getInternsEnrolled(), dataCellStyle);
        createDataRow(sheet, 2, 2, "Thực tập sinh tốt nghiệp", trainingStats.getGraduatingInterns(), dataCellStyle);
        createDataRow(sheet, 3, 3, "Thực tập sinh fail", trainingStats.getInternsFailed(), dataCellStyle);
        createDataRow(sheet, 4, 4, "Tỷ lệ pass/ fail", trainingStats.getRate(), dataCellStyle);
        createDataRow(sheet, 5, 5, "Thực tập sinh đang thực tập", trainingStats.getInternsCurrentlyPracticing(), dataCellStyle);
        createDataRow(sheet, 6, 6, "Thực tập sinh nghỉ thực tập", trainingStats.getInternsQuitInternship(), dataCellStyle);
        createDataRow(sheet, 7, 7, "Điểm tốt nghiệp trung bình", trainingStats.getAverageGraduationScore(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(sheet, 200);

        // Populate "Các môn học" sheet
        Sheet subjectSheet = workbook.createSheet("Các môn học");
        createSubjectHeaderRow(subjectSheet, headerCellStyle);

        int rowNum = 1;
        for (AverageOfSubjectDTO subject : trainingStats.getAverageOfSubject()) {
            createSubjectDataRow(subjectSheet, rowNum++, subject, dataCellStyle);
        }

        // Set column widths to 200 pixels
        setColumnWidths(subjectSheet, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }

    private void createHeaderRow(Sheet sheet, CellStyle headerCellStyle) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Tiêu chí");
        headerRow.createCell(2).setCellValue("Giá trị");
        headerRow.getCell(0).setCellStyle(headerCellStyle);
        headerRow.getCell(1).setCellStyle(headerCellStyle);
        headerRow.getCell(2).setCellStyle(headerCellStyle);
    }

    private void createDataRow(Sheet sheet, int rowIndex, int id, String label, Object value, CellStyle cellStyle) {
        Row dataRow = sheet.createRow(rowIndex);
        dataRow.createCell(0).setCellValue(id);
        dataRow.createCell(1).setCellValue(label);
        if (value instanceof Number) {
            dataRow.createCell(2).setCellValue(((Number) value).doubleValue());
        } else if (value instanceof String) {
            dataRow.createCell(2).setCellValue((String) value);
        } else {
            dataRow.createCell(2).setCellValue(value != null ? value.toString() : "");
        }
        dataRow.getCell(0).setCellStyle(cellStyle);
        dataRow.getCell(1).setCellStyle(cellStyle);
        dataRow.getCell(2).setCellStyle(cellStyle);
    }

    private void createSubjectHeaderRow(Sheet sheet, CellStyle headerCellStyle) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Môn học");
        headerRow.createCell(2).setCellValue("Điểm trung bình");
        headerRow.getCell(0).setCellStyle(headerCellStyle);
        headerRow.getCell(1).setCellStyle(headerCellStyle);
        headerRow.getCell(2).setCellStyle(headerCellStyle);
    }

    private void createSubjectDataRow(Sheet sheet, int rowIndex, AverageOfSubjectDTO subject, CellStyle cellStyle) {
        Row dataRow = sheet.createRow(rowIndex);
        dataRow.createCell(0).setCellValue(rowIndex); // Assuming ID is just the row number
        dataRow.createCell(1).setCellValue(subject.getSubjectName());
        dataRow.createCell(2).setCellValue(subject.getAverage());
        dataRow.getCell(0).setCellStyle(cellStyle);
        dataRow.getCell(1).setCellStyle(cellStyle);
        dataRow.getCell(2).setCellStyle(cellStyle);
    }

    private void setColumnWidths(Sheet sheet, int pixelWidth) {
        int widthInUnits = pixel2WidthUnits(pixelWidth);
        int numberOfColumns = sheet.getRow(0).getLastCellNum(); // Get number of columns from the header row
        for (int i = 0; i < numberOfColumns; i++) {
            sheet.setColumnWidth(i, widthInUnits);
        }
    }

    private int pixel2WidthUnits(int px) {
        // Approximate conversion from pixels to character width units
        return (int) ((px - 5) / 7 * 256);
    }

    public ByteArrayOutputStream exportRecruitmentStatsToExcel() throws IOException {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        int currentQuarter = (currentMonth - 1) / 3 + 1;

        RecruitmentStatsDTO recruitmentStatsWithAll = recruitmentStatsService.getAllRecruitmentStats();
        RecruitmentStatsDTO recruitmentStatsWithMonth = recruitmentStatsService.getRecruitmentStatsByMonth(currentMonth, currentYear);
        RecruitmentStatsDTO recruitmentStatsWithQuarter = recruitmentStatsService.getRecruitmentStatsByQuarter(currentQuarter, currentYear);
        RecruitmentStatsDTO recruitmentStatsWithYear = recruitmentStatsService.getRecruitmentStatsByYear(currentYear);

        Workbook recruitmentWorkBook = new XSSFWorkbook();
        Sheet sheet = recruitmentWorkBook.createSheet("Tất cả hệ thống");

        // Create font for headers
        Font headerFont = recruitmentWorkBook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14); // Set font size to 14

        // Create style for headers
        CellStyle headerCellStyle = recruitmentWorkBook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Tiêu chí");
        headerRow.createCell(2).setCellValue("Giá trị");
        headerRow.getCell(0).setCellStyle(headerCellStyle);
        headerRow.getCell(1).setCellStyle(headerCellStyle);
        headerRow.getCell(2).setCellStyle(headerCellStyle);

        // Create style for data
        CellStyle dataCellStyle = recruitmentWorkBook.createCellStyle();
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Populate "Tất cả hệ thống" sheet
        createDataRow(sheet, 1, 1, "Số CV mới", recruitmentStatsWithAll.getTotalCV(), dataCellStyle);
        createDataRow(sheet, 2, 2, "Số CV phỏng vấn", recruitmentStatsWithAll.getTotalInterviewCV(), dataCellStyle);
        createDataRow(sheet, 3, 3, "Số ứng viên đã phỏng vấn", recruitmentStatsWithAll.getCandidatesInterview(), dataCellStyle);
        createDataRow(sheet, 4, 4, "Số ứng iên không đến phỏng vấn", recruitmentStatsWithAll.getCandidatesDoNotInterview(), dataCellStyle);
        createDataRow(sheet, 5, 5, "Số pass", recruitmentStatsWithAll.getCandidatesPass(), dataCellStyle);
        createDataRow(sheet, 6, 6, "Số fail", recruitmentStatsWithAll.getCandidatesFail(), dataCellStyle);
        createDataRow(sheet, 7, 7, "Số ứng viên nhận việc", recruitmentStatsWithAll.getCandidatesAcceptJob(), dataCellStyle);
        createDataRow(sheet, 8, 8, "Số ứng viên không nhận việc", recruitmentStatsWithAll.getCandidatesRejectJob(), dataCellStyle);
        createDataRow(sheet, 9, 9, "Số ứng viên chưa nhận việc", recruitmentStatsWithAll.getCandidatesAcceptJobYet(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(sheet, 200);

        // Populate "Tháng" sheet
        Sheet monthSheet = recruitmentWorkBook.createSheet("Tháng");
        createHeaderRow(monthSheet, headerCellStyle);
        createDataRow(monthSheet, 1, 1, "Số CV mới", recruitmentStatsWithMonth.getTotalCV(), dataCellStyle);
        createDataRow(monthSheet, 2, 2, "Số CV phỏng vấn", recruitmentStatsWithMonth.getTotalInterviewCV(), dataCellStyle);
        createDataRow(monthSheet, 3, 3, "Số ứng viên đã phỏng vấn", recruitmentStatsWithMonth.getCandidatesInterview(), dataCellStyle);
        createDataRow(monthSheet, 4, 4, "Số ứng iên không đến phỏng vấn", recruitmentStatsWithMonth.getCandidatesDoNotInterview(), dataCellStyle);
        createDataRow(monthSheet, 5, 5, "Số pass", recruitmentStatsWithMonth.getCandidatesPass(), dataCellStyle);
        createDataRow(monthSheet, 6, 6, "Số fail", recruitmentStatsWithMonth.getCandidatesFail(), dataCellStyle);
        createDataRow(monthSheet, 7, 7, "Số ứng viên nhận việc", recruitmentStatsWithMonth.getCandidatesAcceptJob(), dataCellStyle);
        createDataRow(monthSheet, 8, 8, "Số ứng viên không nhận việc", recruitmentStatsWithMonth.getCandidatesRejectJob(), dataCellStyle);
        createDataRow(monthSheet, 9, 9, "Số ứng viên chưa nhận việc", recruitmentStatsWithMonth.getCandidatesAcceptJobYet(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(monthSheet, 200);

        // Populate "Quý" sheet
        Sheet quarterSheet = recruitmentWorkBook.createSheet("Quý");
        createHeaderRow(quarterSheet, headerCellStyle);
        createDataRow(quarterSheet, 1, 1, "Số CV mới", recruitmentStatsWithQuarter.getTotalCV(), dataCellStyle);
        createDataRow(quarterSheet, 2, 2, "Số CV phỏng vấn", recruitmentStatsWithQuarter.getTotalInterviewCV(), dataCellStyle);
        createDataRow(quarterSheet, 3, 3, "Số ứng viên đã phỏng vấn", recruitmentStatsWithQuarter.getCandidatesInterview(), dataCellStyle);
        createDataRow(quarterSheet, 4, 4, "Số ứng iên không đến phỏng vấn", recruitmentStatsWithQuarter.getCandidatesDoNotInterview(), dataCellStyle);
        createDataRow(quarterSheet, 5, 5, "Số pass", recruitmentStatsWithQuarter.getCandidatesPass(), dataCellStyle);
        createDataRow(quarterSheet, 6, 6, "Số fail", recruitmentStatsWithQuarter.getCandidatesFail(), dataCellStyle);
        createDataRow(quarterSheet, 7, 7, "Số ứng viên nhận việc", recruitmentStatsWithQuarter.getCandidatesAcceptJob(), dataCellStyle);
        createDataRow(quarterSheet, 8, 8, "Số ứng viên không nhận việc", recruitmentStatsWithQuarter.getCandidatesRejectJob(), dataCellStyle);
        createDataRow(quarterSheet, 9, 9, "Số ứng viên chưa nhận việc", recruitmentStatsWithQuarter.getCandidatesAcceptJobYet(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(quarterSheet, 200);

        // Populate "Năm" sheet
        Sheet yearSheet = recruitmentWorkBook.createSheet("Năm");
        createHeaderRow(yearSheet, headerCellStyle);
        createDataRow(yearSheet, 1, 1, "Số CV mới", recruitmentStatsWithYear.getTotalCV(), dataCellStyle);
        createDataRow(yearSheet, 2, 2, "Số CV phỏng vấn", recruitmentStatsWithYear.getTotalInterviewCV(), dataCellStyle);
        createDataRow(yearSheet, 3, 3, "Số ứng viên đã phỏng vấn", recruitmentStatsWithYear.getCandidatesInterview(), dataCellStyle);
        createDataRow(yearSheet, 4, 4, "Số ứng iên không đến phỏng vấn", recruitmentStatsWithYear.getCandidatesDoNotInterview(), dataCellStyle);
        createDataRow(yearSheet, 5, 5, "Số pass", recruitmentStatsWithYear.getCandidatesPass(), dataCellStyle);
        createDataRow(yearSheet, 6, 6, "Số fail", recruitmentStatsWithYear.getCandidatesFail(), dataCellStyle);
        createDataRow(yearSheet, 7, 7, "Số ứng viên nhận việc", recruitmentStatsWithYear.getCandidatesAcceptJob(), dataCellStyle);
        createDataRow(yearSheet, 8, 8, "Số ứng viên không nhận việc", recruitmentStatsWithYear.getCandidatesRejectJob(), dataCellStyle);
        createDataRow(yearSheet, 9, 9, "Số ứng viên chưa nhận việc", recruitmentStatsWithYear.getCandidatesAcceptJobYet(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(yearSheet, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        recruitmentWorkBook.write(outputStream);
        recruitmentWorkBook.close();

        return outputStream;
    }

    private ByteArrayOutputStream createExcelRecruitment(RecruitmentStatsDTO recruitmentStats, String sheetName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // Create font for headers
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14); // Set font size to 14

        // Create style for headers
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Tiêu chí");
        headerRow.createCell(2).setCellValue("Giá trị");
        headerRow.getCell(0).setCellStyle(headerCellStyle);
        headerRow.getCell(1).setCellStyle(headerCellStyle);
        headerRow.getCell(2).setCellStyle(headerCellStyle);

        // Create style for data
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Populate the sheet
        createDataRow(sheet, 1, 1, "Số CV mới", recruitmentStats.getTotalCV(), dataCellStyle);
        createDataRow(sheet, 2, 2, "Số CV phỏng vấn", recruitmentStats.getTotalInterviewCV(), dataCellStyle);
        createDataRow(sheet, 3, 3, "Số ứng viên đã phỏng vấn", recruitmentStats.getCandidatesInterview(), dataCellStyle);
        createDataRow(sheet, 4, 4, "Số ứng iên không đến phỏng vấn", recruitmentStats.getCandidatesDoNotInterview(), dataCellStyle);
        createDataRow(sheet, 5, 5, "Số pass", recruitmentStats.getCandidatesPass(), dataCellStyle);
        createDataRow(sheet, 6, 6, "Số fail", recruitmentStats.getCandidatesFail(), dataCellStyle);
        createDataRow(sheet, 7, 7, "Số ứng viên nhận việc", recruitmentStats.getCandidatesAcceptJob(), dataCellStyle);
        createDataRow(sheet, 8, 8, "Số ứng viên không nhận việc", recruitmentStats.getCandidatesRejectJob(), dataCellStyle);
        createDataRow(sheet, 9, 9, "Số ứng viên chưa nhận việc", recruitmentStats.getCandidatesAcceptJobYet(), dataCellStyle);

        // Set column widths to 200 pixels
        setColumnWidths(sheet, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }

    public ByteArrayOutputStream exportRecruitmentStatsToExcelByMonth(int month, int year) throws IOException {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        RecruitmentStatsDTO recruitmentStatsWithMonth = recruitmentStatsService.getRecruitmentStatsByMonth(month, currentYear);
        return createExcelRecruitment(recruitmentStatsWithMonth, "Tháng");
    }

    public ByteArrayOutputStream exportRecruitmentStatsToExcelByQuarter(int quarter, int year) throws IOException {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        RecruitmentStatsDTO recruitmentStatsWithQuarter = recruitmentStatsService.getRecruitmentStatsByQuarter(quarter, currentYear);
        return createExcelRecruitment(recruitmentStatsWithQuarter, "Quý");
    }

    public ByteArrayOutputStream exportRecruitmentStatsToExcelByYear(int year) throws IOException {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        RecruitmentStatsDTO recruitmentStatsWithYear = recruitmentStatsService.getRecruitmentStatsByYear(currentYear);
        return createExcelRecruitment(recruitmentStatsWithYear, "Năm");
    }
}
