package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.*;
import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.RecruitmentPlan;
import com.example.quanlydaotao.repository.IInternRepository;
import com.example.quanlydaotao.repository.InternProfileRepository;
import com.example.quanlydaotao.service.IInternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InternService implements IInternService {
    @Autowired
    private IInternRepository iInternRepository;
    @Autowired
    private RecruitmentPlanService recruitmentPlanService;
    @Autowired
    private RecruitmentPlanDetailService recruitmentPlanDetailService;
    @Autowired
    private InternServiceImpl internServiceImpl;
    @Autowired
    private InternProfileRepository internProfileRepository;

    @Override
    public void createIntern(Intern intern) {
        iInternRepository.save(intern);
    }

    @Override
    public Page<Intern> showIntern(Pageable pageable) {
        return iInternRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public void updateIntern(Intern intern) throws Exception {
        Optional<RecruitmentPlan> recruitmentPlan = recruitmentPlanService.findById(intern.getRecruitmentPlan().getId());
        intern.setRecruitmentPlan(recruitmentPlan.get());
        if (intern.getStatus().equals("Đã nhận việc")) {
            InternProfile internProfile = new InternProfile(intern);
            internProfile.setIsPass(null);
            internServiceImpl.save(internProfile);
        }
        if (!isFullIntern(intern.getRecruitmentPlan().getId())) {
            iInternRepository.saveAndFlush(intern);
        }else {
            throw new Exception("số lượng của kế hoạch này đã đủ");
        }

    }

    @Override
    public Optional<Intern> getIntern(long id) {
        return iInternRepository.findById(id);
    }


    public void addIntern(Intern intern) throws Exception{
        RecruitmentPlan plan = recruitmentPlanService.findById(intern.getRecruitmentPlan().getId()).get();
        intern.setApplyCVTime(LocalDateTime.now());
        intern.setRecruitmentPlan(plan);
        if (!isFullIntern(intern.getRecruitmentPlan().getId())) {
            iInternRepository.save(intern);
        }else {
            throw new Exception("số lượng của kế hoạch này đã đủ");
        }
    }

    public boolean isFullIntern(long recruitmentPlanId){
        boolean isFull = true;

        int totalInternNeed = recruitmentPlanDetailService.getTotalIntern(recruitmentPlanId);
        int applicants = applicantsByPlan(recruitmentPlanId);

        if (applicants < totalInternNeed) {
            isFull = false;
        }
        return isFull;
    }

    @Override
    public Page<Intern> findAllByNameOrEmail(PaginateRequest paginateRequest, InternSearchDTO internSearchDTO) {
        return iInternRepository.findAll(
                new InternSpec(internSearchDTO),
                PageRequest.of(
                        paginateRequest.getPage(),
                        paginateRequest.getSize(),
                        Sort.by(Sort.Direction.DESC, "id")
                )
        );
    }

    public int applicantsByPlan(long planId) {
        int applicants = iInternRepository.countByRecruitmentPlanId(planId);
        return applicants;
    }

    public int trainingByPlan(long planId) {
        Iterable<Intern> interns = iInternRepository.findByRecruitmentPlanId(planId);
        int training = 0;
        for (Intern intern : interns) {
            if (intern.getStatus().equals("Đã nhận việc")) {
                training++;
            }
        }
        return training;
    }
    public int internPass(long planId) {
        List<Long> interns = new ArrayList<>();
        for (Intern intern : iInternRepository.findByRecruitmentPlanId(planId)) {
            interns.add(intern.getId());
        }
        List<InternProfile> internProfiles = internProfileRepository.findByInternIdIn(interns);


        int resultIntern = 0;
        for (InternProfile internProfile : internProfiles) {
            if (internProfile.getIsPass() == null) {
                  continue;
            }
            if (internProfile.getIsPass() ) {
                resultIntern++;
            }
        }
        return resultIntern;
    }

    public ProcessDTO showProcessIntern(ProcessDTO process) {
        ProcessDTO newProcess = process;
        int applicants = applicantsByPlan(process.getPlanId());
        int training = trainingByPlan(process.getPlanId());

        if (applicants > 0) {
            newProcess.setApplicants(applicants)
                    .setStep(3);
        }

        if (training > 0) {
            newProcess.setTraining(training)
                    .setStep(4);
        }

        newProcess.setTotalIntern(recruitmentPlanDetailService.getTotalResult(newProcess.getPlanId()));
        newProcess.setIntern(internPass(process.getPlanId()));

        return newProcess;
    }

}
