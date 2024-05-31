package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.*;
import com.example.quanlydaotao.repository.IInternRepository;
import com.example.quanlydaotao.repository.IRecruitmentPlanRepository;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import com.example.quanlydaotao.repository.InternProfileRepository;
import com.example.quanlydaotao.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceIMPL implements DashBoardService {
    @Autowired
    IRecruitmentRequestRepository recruitmentRequestRepository;
    @Autowired
    IRecruitmentPlanRepository recruitmentPlanRepository;;
    @Autowired
    IInternRepository internRepository;
    @Autowired
    InternProfileRepository profileRepository;
    @Override
    public PersonnelNeedsDTO getPersonnelNeedsDTO() {
        PersonnelNeedsDTO personnelNeedsDTO = new PersonnelNeedsDTO();
        personnelNeedsDTO.setPersonnelNeeds(recruitmentRequestRepository.findAll().size());
        personnelNeedsDTO.setAwaitingApproval(recruitmentRequestRepository.findByStatusEquals("Đã gửi").size());
        personnelNeedsDTO.setApproved(recruitmentRequestRepository.findByStatusEquals("Đã gửi").size());
        personnelNeedsDTO.setHandedOver(recruitmentRequestRepository.findByStatusEquals("Đã bàn giao").size());

        return personnelNeedsDTO;
    }

    @Override
    public RecruitmentPlanDashBoardDTO getRecruitmentPlanDashBoardDTO() {
        RecruitmentPlanDashBoardDTO recruitmentPlanDTO = new RecruitmentPlanDashBoardDTO();
        recruitmentPlanDTO.setTotalRecruitmentPlan(recruitmentPlanRepository.findAll().size());
        recruitmentPlanDTO.setAwaitingApproval(recruitmentPlanRepository.findByStatusEquals("Đang chờ duyệt").size());
        recruitmentPlanDTO.setApproved(recruitmentRequestRepository.findByStatusEquals("Đã duyệt").size());
        recruitmentPlanDTO.setAccomplished(recruitmentPlanRepository.findByStatusEquals("Đã hoàn thành").size());

        return recruitmentPlanDTO;
    }

    @Override
    public CandidateDTO getCandidateDTO() {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setTotalCandidate(internRepository.findAll().size());
        candidateDTO.setHaveNotInterviewedYet(internRepository.findByStatusEquals(null).size());
        candidateDTO.setCandidatePass(internRepository.findByFinalResultEquals("true").size());
        candidateDTO.setCandidateFail(internRepository.findByFinalResultEquals("false").size());
        return candidateDTO;
    }

    @Override
    public InternDashBoardDTO getInternDTO() {
        InternDashBoardDTO internDTO = new InternDashBoardDTO();
        internDTO.setTotalIntern(profileRepository.findAll().size());
        internDTO.setInternTraining(profileRepository.findByTrainingStateEquals("training").size());
        internDTO.setInternPass(profileRepository.findByIsPassEquals(true).size());
        internDTO.setInternFail(profileRepository.findByIsPassEquals(false).size());
        return internDTO;
    }
}
