package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.RecruitmentStatsDTO;
import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.repository.IInternRepository;
import com.example.quanlydaotao.service.RecruitmentStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitmentStatsServiceIMPL implements RecruitmentStatsService {
    @Autowired
    IInternRepository internRepository;

    @Override
    public RecruitmentStatsDTO getRecruitmentStatsByMonth(int month, int year) {
        RecruitmentStatsDTO recruitmentStatsDTO = new RecruitmentStatsDTO();
        List<Intern> totalCandidates = internRepository.findAll();
        List<Intern> newTotalCandidates = new ArrayList<>();
        for (Intern intern : totalCandidates) {
            if (intern.getApplyCVTime().getMonthValue() == month && intern.getApplyCVTime().getYear() == year) {
                newTotalCandidates.add(intern);
            }
        }
        recruitmentStatsDTO.setTotalCV(newTotalCandidates.size());

        List<Intern> candidatesInterview = internRepository.findByCheckInterviewEquals(true);
        List<Intern> newCandidatesInterview = new ArrayList<>();
        for (Intern intern : candidatesInterview) {
            if (intern.getApplyCVTime().getMonthValue() == month && intern.getApplyCVTime().getYear() == year) {
                newCandidatesInterview.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesInterview(newCandidatesInterview.size());

        List<Intern> candidatesDoNotInterview = internRepository.findByCheckInterviewEquals(false);
        List<Intern> newCandidatesDoNotInterview = new ArrayList<>();
        for (Intern intern : candidatesDoNotInterview) {
            if (intern.getApplyCVTime().getMonthValue() == month && intern.getApplyCVTime().getYear() == year) {
                newCandidatesDoNotInterview.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesDoNotInterview(newCandidatesDoNotInterview.size());

        recruitmentStatsDTO.setTotalInterviewCV(newCandidatesInterview.size() + newCandidatesDoNotInterview.size());

        List<Intern> candidatesPass = internRepository.findByFinalResultEquals("true");
        List<Intern> newCandidatesPass = new ArrayList<>();
        for (Intern intern : candidatesPass) {
            if (intern.getApplyCVTime().getMonthValue() == month && intern.getApplyCVTime().getYear() == year) {
                newCandidatesPass.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesPass(newCandidatesPass.size());

        List<Intern> candidatesNotPass = internRepository.findByFinalResultEquals("false");
        List<Intern> newCandidatesNotPass = new ArrayList<>();
        for (Intern intern : candidatesNotPass) {
            if (intern.getApplyCVTime().getMonthValue() == month && intern.getApplyCVTime().getYear() == year) {
                newCandidatesNotPass.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesFail(newCandidatesNotPass.size());



        List<Intern> candidatesAcceptJob = internRepository.findByStatusEquals("Đã nhận việc");
        List<Intern> newCandidatesAcceptJob = new ArrayList<>();
        for (Intern intern : candidatesAcceptJob) {
            if (intern.getApplyCVTime().getMonthValue() == month && intern.getApplyCVTime().getYear() == year) {
                newCandidatesAcceptJob.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesAcceptJob(newCandidatesAcceptJob.size());

        List<Intern> candidatesRejectJob = internRepository.findByStatusEquals("Không nhận việc");
        List<Intern> newCandidatesRejectJob = new ArrayList<>();
        for (Intern intern : candidatesRejectJob) {
            if (intern.getApplyCVTime().getMonthValue() == month && intern.getApplyCVTime().getYear() == year) {
                newCandidatesRejectJob.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesRejectJob(newCandidatesRejectJob.size());

        recruitmentStatsDTO.setCandidatesAcceptJobYet(newCandidatesPass.size() - newCandidatesAcceptJob.size() - newCandidatesRejectJob.size() );

        return recruitmentStatsDTO;
    }

    @Override
    public RecruitmentStatsDTO getRecruitmentStatsByQuarter(int quarter, int year) {
        RecruitmentStatsDTO recruitmentStatsDTO = new RecruitmentStatsDTO();
        List<Intern> totalCandidates = internRepository.findAll();
        List<Intern> newTotalCandidates = new ArrayList<>();
        for (Intern intern : totalCandidates) {
            if (((intern.getApplyCVTime().getMonthValue() -1)/ 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newTotalCandidates.add(intern);
            }
        }
        recruitmentStatsDTO.setTotalCV(newTotalCandidates.size());

        List<Intern> candidatesInterview = internRepository.findByCheckInterviewEquals(true);
        List<Intern> newCandidatesInterview = new ArrayList<>();
        for (Intern intern : candidatesInterview) {
            if (((intern.getApplyCVTime().getMonthValue() -1)/ 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesInterview.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesInterview(newCandidatesInterview.size());

        List<Intern> candidatesDoNotInterview = internRepository.findByCheckInterviewEquals(false);
        List<Intern> newCandidatesDoNotInterview = new ArrayList<>();
        for (Intern intern : candidatesDoNotInterview) {
            if (((intern.getApplyCVTime().getMonthValue() -1)/ 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesDoNotInterview.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesDoNotInterview(newCandidatesDoNotInterview.size());

        recruitmentStatsDTO.setTotalInterviewCV(newCandidatesInterview.size() + newCandidatesDoNotInterview.size());

        List<Intern> candidatesPass = internRepository.findByFinalResultEquals("true");
        List<Intern> newCandidatesPass = new ArrayList<>();
        for (Intern intern : candidatesPass) {
            if (((intern.getApplyCVTime().getMonthValue() -1)/ 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesPass.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesPass(newCandidatesPass.size());

        List<Intern> candidatesNotPass = internRepository.findByFinalResultEquals("false");
        List<Intern> newCandidatesNotPass = new ArrayList<>();
        for (Intern intern : candidatesNotPass) {
            if (((intern.getApplyCVTime().getMonthValue() -1)/ 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesNotPass.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesFail(newCandidatesNotPass.size());



        List<Intern> candidatesAcceptJob = internRepository.findByStatusEquals("Đã nhận việc");
        List<Intern> newCandidatesAcceptJob = new ArrayList<>();
        for (Intern intern : candidatesAcceptJob) {
            if (((intern.getApplyCVTime().getMonthValue() -1)/ 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesAcceptJob.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesAcceptJob(newCandidatesAcceptJob.size());

        List<Intern> candidatesRejectJob = internRepository.findByStatusEquals("Không nhận việc");
        List<Intern> newCandidatesRejectJob = new ArrayList<>();
        for (Intern intern : candidatesRejectJob) {
            if (((intern.getApplyCVTime().getMonthValue() -1)/ 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesRejectJob.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesRejectJob(newCandidatesRejectJob.size());

        recruitmentStatsDTO.setCandidatesAcceptJobYet(newCandidatesPass.size() - newCandidatesAcceptJob.size() - newCandidatesRejectJob.size() );

        return recruitmentStatsDTO;
    }

    @Override
    public RecruitmentStatsDTO getRecruitmentStatsByYear(int year) {
        RecruitmentStatsDTO recruitmentStatsDTO = new RecruitmentStatsDTO();
        List<Intern> totalCandidates = internRepository.findAll();
        List<Intern> newTotalCandidates = new ArrayList<>();
        for (Intern intern : totalCandidates) {
            if (intern.getApplyCVTime().getYear() == year) {
                newTotalCandidates.add(intern);
            }
        }
        recruitmentStatsDTO.setTotalCV(newTotalCandidates.size());

        List<Intern> candidatesInterview = internRepository.findByCheckInterviewEquals(true);
        List<Intern> newCandidatesInterview = new ArrayList<>();
        for (Intern intern : candidatesInterview) {
            if (intern.getApplyCVTime().getYear() == year) {
                newCandidatesInterview.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesInterview(newCandidatesInterview.size());

        List<Intern> candidatesDoNotInterview = internRepository.findByCheckInterviewEquals(false);
        List<Intern> newCandidatesDoNotInterview = new ArrayList<>();
        for (Intern intern : candidatesDoNotInterview) {
            if (intern.getApplyCVTime().getYear() == year) {
                newCandidatesDoNotInterview.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesDoNotInterview(newCandidatesDoNotInterview.size());

        recruitmentStatsDTO.setTotalInterviewCV(newCandidatesInterview.size() + newCandidatesDoNotInterview.size());

        List<Intern> candidatesPass = internRepository.findByFinalResultEquals("true");
        List<Intern> newCandidatesPass = new ArrayList<>();
        for (Intern intern : candidatesPass) {
            if (intern.getApplyCVTime().getYear() == year) {
                newCandidatesPass.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesPass(newCandidatesPass.size());

        List<Intern> candidatesNotPass = internRepository.findByFinalResultEquals("false");
        List<Intern> newCandidatesNotPass = new ArrayList<>();
        for (Intern intern : candidatesNotPass) {
            if (intern.getApplyCVTime().getYear() == year) {
                newCandidatesNotPass.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesFail(newCandidatesNotPass.size());



        List<Intern> candidatesAcceptJob = internRepository.findByStatusEquals("Đã nhận việc");
        List<Intern> newCandidatesAcceptJob = new ArrayList<>();
        for (Intern intern : candidatesAcceptJob) {
            if (intern.getApplyCVTime().getYear() == year) {
                newCandidatesAcceptJob.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesAcceptJob(newCandidatesAcceptJob.size());

        List<Intern> candidatesRejectJob = internRepository.findByStatusEquals("Không nhận việc");
        List<Intern> newCandidatesRejectJob = new ArrayList<>();
        for (Intern intern : candidatesRejectJob) {
            if (intern.getApplyCVTime().getYear() == year) {
                newCandidatesRejectJob.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesRejectJob(newCandidatesRejectJob.size());

        recruitmentStatsDTO.setCandidatesAcceptJobYet(newCandidatesPass.size() - newCandidatesAcceptJob.size() - newCandidatesRejectJob.size() );

        return recruitmentStatsDTO;
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

        recruitmentStatsDTO.setCandidatesAcceptJobYet(candidatesPass.size() - candidatesAcceptJob.size() - candidatesRejectJob.size() );

        return recruitmentStatsDTO;
    }

    @Override
    public RecruitmentStatsDTO getMaxRecruitmentStatsWithYear() {
        RecruitmentStatsDTO recruitmentStatsDTO = getRecruitmentStatsByYear(2020);
        RecruitmentStatsDTO recruitmentStatsDTO1 = getRecruitmentStatsByYear(2021);
        RecruitmentStatsDTO recruitmentStatsDTO2 = getRecruitmentStatsByYear(2022);
        RecruitmentStatsDTO recruitmentStatsDTO3 = getRecruitmentStatsByYear(2023);
        RecruitmentStatsDTO recruitmentStatsDTO4 = getRecruitmentStatsByYear(2024);
        List<RecruitmentStatsDTO> recruitmentStatsDTOList = new ArrayList<>();
        recruitmentStatsDTOList.add(recruitmentStatsDTO);
        recruitmentStatsDTOList.add(recruitmentStatsDTO1);
        recruitmentStatsDTOList.add(recruitmentStatsDTO2);
        recruitmentStatsDTOList.add(recruitmentStatsDTO3);
        recruitmentStatsDTOList.add(recruitmentStatsDTO4);

        int totalCV = 0;
        int totalInterviewCV = 0;
        int candidatesInterview = 0;
        int candidatesDoNotInterview = 0;
        int candidatesPass = 0;
        int candidatesFail = 0;
        int candidatesAcceptJob = 0;
        int candidatesRejectJob = 0;
        int candidatesAcceptJobYet = 0;

        for (RecruitmentStatsDTO res : recruitmentStatsDTOList) {
            if (totalCV < res.getTotalCV()) {
                totalCV = res.getTotalCV();
            }
            if (totalInterviewCV < res.getTotalInterviewCV()) {
                totalInterviewCV = res.getTotalInterviewCV();
            }
            if (candidatesInterview < res.getCandidatesInterview()) {
                candidatesInterview = res.getCandidatesInterview();
            }
            if (candidatesDoNotInterview < res.getCandidatesDoNotInterview()) {
                candidatesDoNotInterview = res.getCandidatesDoNotInterview();
            }
            if (candidatesPass < res.getCandidatesPass()) {
                candidatesPass = res.getCandidatesPass();
            }
            if (candidatesFail < res.getCandidatesFail()) {
                candidatesFail = res.getCandidatesFail();
            }
            if (candidatesAcceptJob < res.getCandidatesAcceptJob()) {
                candidatesAcceptJob = res.getCandidatesAcceptJob();
            }
            if (candidatesRejectJob < res.getCandidatesRejectJob()) {
                candidatesRejectJob = res.getCandidatesRejectJob();
            }
            if (candidatesAcceptJobYet < res.getCandidatesAcceptJobYet()) {
                candidatesAcceptJobYet = res.getCandidatesAcceptJobYet();
            }
        }
        RecruitmentStatsDTO maxRecruitmentStatsDTO = new RecruitmentStatsDTO(totalCV, totalInterviewCV, candidatesInterview, candidatesDoNotInterview, candidatesPass, candidatesFail, candidatesAcceptJob, candidatesRejectJob, candidatesAcceptJobYet);
        return maxRecruitmentStatsDTO;
    }

    @Override
    public RecruitmentStatsDTO getMaxRecruitmentStatsWithMonth() {
        RecruitmentStatsDTO recruitmentStatsDTO = getRecruitmentStatsByMonth(1,2024);
        RecruitmentStatsDTO recruitmentStatsDTO1 = getRecruitmentStatsByMonth(2,2024);
        RecruitmentStatsDTO recruitmentStatsDTO2 = getRecruitmentStatsByMonth(3,2024);
        RecruitmentStatsDTO recruitmentStatsDTO3 = getRecruitmentStatsByMonth(4,2024);
        RecruitmentStatsDTO recruitmentStatsDTO4 = getRecruitmentStatsByMonth(5,2024);
        RecruitmentStatsDTO recruitmentStatsDTO5 = getRecruitmentStatsByMonth(6,2024);
        RecruitmentStatsDTO recruitmentStatsDTO6 = getRecruitmentStatsByMonth(7,2024);
        RecruitmentStatsDTO recruitmentStatsDTO7 = getRecruitmentStatsByMonth(8,2024);
        RecruitmentStatsDTO recruitmentStatsDTO8 = getRecruitmentStatsByMonth(9,2024);
        RecruitmentStatsDTO recruitmentStatsDTO9 = getRecruitmentStatsByMonth(10,2024);
        RecruitmentStatsDTO recruitmentStatsDTO10 = getRecruitmentStatsByMonth(11,2024);
        RecruitmentStatsDTO recruitmentStatsDTO11 = getRecruitmentStatsByMonth(12,2024);
        List<RecruitmentStatsDTO> list = new ArrayList<>();
        list.add(recruitmentStatsDTO);
        list.add(recruitmentStatsDTO1);
        list.add(recruitmentStatsDTO2);
        list.add(recruitmentStatsDTO3);
        list.add(recruitmentStatsDTO4);
        list.add(recruitmentStatsDTO5);
        list.add(recruitmentStatsDTO6);
        list.add(recruitmentStatsDTO7);
        list.add(recruitmentStatsDTO8);
        list.add(recruitmentStatsDTO9);
        list.add(recruitmentStatsDTO10);
        list.add(recruitmentStatsDTO11);

        int totalCV = 0;
        int totalInterviewCV = 0;
        int candidatesInterview = 0;
        int candidatesDoNotInterview = 0;
        int candidatesPass = 0;
        int candidatesFail = 0;
        int candidatesAcceptJob = 0;
        int candidatesRejectJob = 0;
        int candidatesAcceptJobYet = 0;

        for (RecruitmentStatsDTO res : list) {
            if (totalCV < res.getTotalCV()) {
                totalCV = res.getTotalCV();
            }
            if (totalInterviewCV < res.getTotalInterviewCV()) {
                totalInterviewCV = res.getTotalInterviewCV();
            }
            if (candidatesInterview < res.getCandidatesInterview()) {
                candidatesInterview = res.getCandidatesInterview();
            }
            if (candidatesDoNotInterview < res.getCandidatesDoNotInterview()) {
                candidatesDoNotInterview = res.getCandidatesDoNotInterview();
            }
            if (candidatesPass < res.getCandidatesPass()) {
                candidatesPass = res.getCandidatesPass();
            }
            if (candidatesFail < res.getCandidatesFail()) {
                candidatesFail = res.getCandidatesFail();
            }
            if (candidatesAcceptJob < res.getCandidatesAcceptJob()) {
                candidatesAcceptJob = res.getCandidatesAcceptJob();
            }
            if (candidatesRejectJob < res.getCandidatesRejectJob()) {
                candidatesRejectJob = res.getCandidatesRejectJob();
            }
            if (candidatesAcceptJobYet < res.getCandidatesAcceptJobYet()) {
                candidatesAcceptJobYet = res.getCandidatesAcceptJobYet();
            }
        }
        RecruitmentStatsDTO maxRecruitmentStatsDTO = new RecruitmentStatsDTO(totalCV, totalInterviewCV, candidatesInterview, candidatesDoNotInterview, candidatesPass, candidatesFail, candidatesAcceptJob, candidatesRejectJob, candidatesAcceptJobYet);
        return maxRecruitmentStatsDTO;
    }

    @Override
    public RecruitmentStatsDTO getMaxRecruitmentStatsWithQuarter() {
        RecruitmentStatsDTO recruitmentStatsDTO = getRecruitmentStatsByQuarter(1,2024);
        RecruitmentStatsDTO recruitmentStatsDTO1 = getRecruitmentStatsByQuarter(2,2024);
        RecruitmentStatsDTO recruitmentStatsDTO2 = getRecruitmentStatsByQuarter(3,2024);
        RecruitmentStatsDTO recruitmentStatsDTO3 = getRecruitmentStatsByQuarter(4,2024);
        List<RecruitmentStatsDTO> list = new ArrayList<>();
        list.add(recruitmentStatsDTO);
        list.add(recruitmentStatsDTO1);
        list.add(recruitmentStatsDTO2);
        list.add(recruitmentStatsDTO3);
        int totalCV = 0;
        int totalInterviewCV = 0;
        int candidatesInterview = 0;
        int candidatesDoNotInterview = 0;
        int candidatesPass = 0;
        int candidatesFail = 0;
        int candidatesAcceptJob = 0;
        int candidatesRejectJob = 0;
        int candidatesAcceptJobYet = 0;

        for (RecruitmentStatsDTO res : list) {
            if (totalCV < res.getTotalCV()) {
                totalCV = res.getTotalCV();
            }
            if (totalInterviewCV < res.getTotalInterviewCV()) {
                totalInterviewCV = res.getTotalInterviewCV();
            }
            if (candidatesInterview < res.getCandidatesInterview()) {
                candidatesInterview = res.getCandidatesInterview();
            }
            if (candidatesDoNotInterview < res.getCandidatesDoNotInterview()) {
                candidatesDoNotInterview = res.getCandidatesDoNotInterview();
            }
            if (candidatesPass < res.getCandidatesPass()) {
                candidatesPass = res.getCandidatesPass();
            }
            if (candidatesFail < res.getCandidatesFail()) {
                candidatesFail = res.getCandidatesFail();
            }
            if (candidatesAcceptJob < res.getCandidatesAcceptJob()) {
                candidatesAcceptJob = res.getCandidatesAcceptJob();
            }
            if (candidatesRejectJob < res.getCandidatesRejectJob()) {
                candidatesRejectJob = res.getCandidatesRejectJob();
            }
            if (candidatesAcceptJobYet < res.getCandidatesAcceptJobYet()) {
                candidatesAcceptJobYet = res.getCandidatesAcceptJobYet();
            }
        }
        RecruitmentStatsDTO maxRecruitmentStatsDTO = new RecruitmentStatsDTO(totalCV, totalInterviewCV, candidatesInterview, candidatesDoNotInterview, candidatesPass, candidatesFail, candidatesAcceptJob, candidatesRejectJob, candidatesAcceptJobYet);
        return maxRecruitmentStatsDTO;
    }
}
