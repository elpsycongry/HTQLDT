package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.RecruitmentStatsDTO;

public interface RecruitmentStatsService {
    public RecruitmentStatsDTO getRecruitmentStatsByMonth(int month, int year);
    public RecruitmentStatsDTO getRecruitmentStatsByQuarter(int month, int quarter);
    public RecruitmentStatsDTO getRecruitmentStatsByYear(int year);
    public RecruitmentStatsDTO getAllRecruitmentStats();
}
