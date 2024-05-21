package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.TrainingStatsDTO;
import org.springframework.data.relational.core.sql.In;

public interface TrainingStatsService {
    TrainingStatsDTO getTrainingStats();
    TrainingStatsDTO getTrainingStatsWithMonth(int month);
    TrainingStatsDTO getTrainingStatsWithQuarter(int quarter);
    TrainingStatsDTO getTrainingStatsWithYear(int year);
}
