package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.RecruitmentStatsDTO;
import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.repository.IInternRepository;
import com.example.quanlydaotao.service.RecruitmentStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentStatsServiceIMPL implements RecruitmentStatsService {
    @Autowired
    IInternRepository internRepository;

    @Override
    public RecruitmentStatsDTO getRecruitmentStatsByMonth(int month, int year) {
        return null;
    }

    @Override
    public RecruitmentStatsDTO getRecruitmentStatsByQuarter(int month, int quarter) {
        return null;
    }

    @Override
    public RecruitmentStatsDTO getRecruitmentStatsByYear(int year) {
        return null;
    }

    @Override
    public RecruitmentStatsDTO getAllRecruitmentStats() {
        RecruitmentStatsDTO recruitmentStatsDTO = new RecruitmentStatsDTO();

        List<Intern> totalCandidates = internRepository.findAll();
        recruitmentStatsDTO.setTotalCV(totalCandidates.size());

        List<Intern> candidatesInterview = internRepository.findByCheckInterviewEquals(true);
        recruitmentStatsDTO.setCandidatesInterview(candidatesInterview.size());

        List<Intern> candidatesDoNotInterview = internRepository.findByCheckInterviewEquals(false);
        recruitmentStatsDTO.setCandidatesDoNotInterview(candidatesDoNotInterview.size());

        recruitmentStatsDTO.setTotalInterviewCV(candidatesInterview.size() + candidatesDoNotInterview.size());

        List<Intern> candidatesPass = internRepository.findByFinalResultEquals("true");
        recruitmentStatsDTO.setCandidatesPass(candidatesPass.size());

        List<Intern> candidatesNotPass = internRepository.findByFinalResultEquals("false");
        recruitmentStatsDTO.setCandidatesFail(candidatesNotPass.size());



        List<Intern> candidatesAcceptJob = internRepository.findByStatusEquals("Đã nhận việc");
        recruitmentStatsDTO.setCandidatesAcceptJob(candidatesAcceptJob.size());

        List<Intern> candidatesRejectJob = internRepository.findByStatusEquals("Không nhận việc");
        recruitmentStatsDTO.setCandidatesRejectJob(candidatesRejectJob.size());

        List<Intern> candidatesAcceptJobYet = internRepository.findByStatusEquals("Chưa nhận việc");
        recruitmentStatsDTO.setCandidatesAcceptJobYet(candidatesAcceptJobYet.size());

        return recruitmentStatsDTO;
    }
}
