package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.model.RecruitmentPlan;
import com.example.quanlydaotao.repository.IInternRepository;
import com.example.quanlydaotao.repository.IRecruitmentPlanRepository;
import com.example.quanlydaotao.service.IInternService;
import com.example.quanlydaotao.service.IRecruitmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InternService implements IInternService {
    @Autowired
    private IInternRepository iInternRepository;
    @Autowired
    private IRecruitmentPlanRepository recruitmentPlanRepository;
    @Override
    public void createIntern(Intern intern) {
        LocalDate localDate = LocalDate.now();
        intern.setDateCreate(localDate);
        iInternRepository.save(intern);
    }

    @Override
    public Page<Intern> showIntern(Pageable pageable) {
        return iInternRepository.findAllByOrderByDateCreateDesc(pageable);
    }

    @Override
    public void updateIntern(Intern intern) {
        Optional<RecruitmentPlan> recruitmentPlan = recruitmentPlanRepository.findById(intern.getRecruitmentPlan().getId());
        intern.setRecruitmentPlan(recruitmentPlan.get());
      iInternRepository.saveAndFlush(intern);
    }

    @Override
    public Optional<Intern> getIntern(long id) {
        return iInternRepository.findById(id);
    }
}
