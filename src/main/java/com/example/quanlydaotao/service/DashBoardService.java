package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.*;

public interface DashBoardService {
    public PersonnelNeedsDTO getPersonnelNeedsDTO();
    public RecruitmentPlanDashBoardDTO getRecruitmentPlanDashBoardDTO();
    public CandidateDTO getCandidateDTO();
    public InternDashBoardDTO getInternDTO();
}
