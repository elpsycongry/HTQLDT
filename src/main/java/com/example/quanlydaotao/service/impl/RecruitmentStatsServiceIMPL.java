package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.RecruitmentStatsDTO;
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
            if ((intern.getApplyCVTime().getMonthValue() / 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newTotalCandidates.add(intern);
            }
        }
        recruitmentStatsDTO.setTotalCV(newTotalCandidates.size());

        List<Intern> candidatesInterview = internRepository.findByCheckInterviewEquals(true);
        List<Intern> newCandidatesInterview = new ArrayList<>();
        for (Intern intern : candidatesInterview) {
            if ((intern.getApplyCVTime().getMonthValue() / 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesInterview.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesInterview(newCandidatesInterview.size());

        List<Intern> candidatesDoNotInterview = internRepository.findByCheckInterviewEquals(false);
        List<Intern> newCandidatesDoNotInterview = new ArrayList<>();
        for (Intern intern : candidatesDoNotInterview) {
            if ((intern.getApplyCVTime().getMonthValue() / 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesDoNotInterview.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesDoNotInterview(newCandidatesDoNotInterview.size());

        recruitmentStatsDTO.setTotalInterviewCV(newCandidatesInterview.size() + newCandidatesDoNotInterview.size());

        List<Intern> candidatesPass = internRepository.findByFinalResultEquals("true");
        List<Intern> newCandidatesPass = new ArrayList<>();
        for (Intern intern : candidatesPass) {
            if ((intern.getApplyCVTime().getMonthValue() / 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesPass.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesPass(newCandidatesPass.size());

        List<Intern> candidatesNotPass = internRepository.findByFinalResultEquals("false");
        List<Intern> newCandidatesNotPass = new ArrayList<>();
        for (Intern intern : candidatesNotPass) {
            if ((intern.getApplyCVTime().getMonthValue() / 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesNotPass.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesFail(newCandidatesNotPass.size());



        List<Intern> candidatesAcceptJob = internRepository.findByStatusEquals("Đã nhận việc");
        List<Intern> newCandidatesAcceptJob = new ArrayList<>();
        for (Intern intern : candidatesAcceptJob) {
            if ((intern.getApplyCVTime().getMonthValue() / 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
                newCandidatesAcceptJob.add(intern);
            }
        }
        recruitmentStatsDTO.setCandidatesAcceptJob(newCandidatesAcceptJob.size());

        List<Intern> candidatesRejectJob = internRepository.findByStatusEquals("Không nhận việc");
        List<Intern> newCandidatesRejectJob = new ArrayList<>();
        for (Intern intern : candidatesRejectJob) {
            if ((intern.getApplyCVTime().getMonthValue() / 3) + 1 == quarter && intern.getApplyCVTime().getYear() == year) {
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
}
