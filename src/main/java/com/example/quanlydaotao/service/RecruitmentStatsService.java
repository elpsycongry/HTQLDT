package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.RecruitmentStatsDTO;

public interface RecruitmentStatsService {
    public RecruitmentStatsDTO getRecruitmentStatsByMonth(int month, int year);
    public RecruitmentStatsDTO getRecruitmentStatsByQuarter(int quarter, int year);
    public RecruitmentStatsDTO getRecruitmentStatsByYear(int year);
    public RecruitmentStatsDTO getAllRecruitmentStats();
    public RecruitmentStatsDTO getMaxRecruitmentStatsWithYear();
    public RecruitmentStatsDTO getMaxRecruitmentStatsWithMonth();
    public RecruitmentStatsDTO getMaxRecruitmentStatsWithQuarter();
}
