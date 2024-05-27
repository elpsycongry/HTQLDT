package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.AverageOfSubjectDTO;
import com.example.quanlydaotao.dto.TrainingStatsDTO;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface TrainingStatsService {
    TrainingStatsDTO getTrainingStats();
    TrainingStatsDTO getTrainingStatsWithMonth(int month, int year);
    TrainingStatsDTO getTrainingStatsWithQuarter(int quarter, int year);
    TrainingStatsDTO getTrainingStatsWithYear(int year);
    List<AverageOfSubjectDTO> getAllAverageOfSubject();
    TrainingStatsDTO getMaxTrainingStatsWithYear();
    TrainingStatsDTO getMaxTrainingStatsWithMonth();
    TrainingStatsDTO getMaxTrainingStatsWithQuarter();
}
