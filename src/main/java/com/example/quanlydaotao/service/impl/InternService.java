package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.*;
import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.model.RecruitmentPlan;
import com.example.quanlydaotao.repository.IInternRepository;
import com.example.quanlydaotao.service.IInternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternService implements IInternService {
    @Autowired
    private IInternRepository iInternRepository;
    @Autowired
    private RecruitmentPlanService recruitmentPlanService;
    @Autowired
    private RecruitmentPlanDetailService recruitmentPlanDetailService;

    @Override
    public void createIntern(Intern intern) {
        iInternRepository.save(intern);
    }

    @Override
    public Page<Intern> showIntern(Pageable pageable) {
        return iInternRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public void updateIntern(Intern intern) {
        Optional<RecruitmentPlan> recruitmentPlan = recruitmentPlanService.findById(intern.getRecruitmentPlan().getId());
        intern.setRecruitmentPlan(recruitmentPlan.get());
        iInternRepository.saveAndFlush(intern);
    }

    @Override
    public Optional<Intern> getIntern(long id) {
        return iInternRepository.findById(id);
    }


    public void addIntern(Intern intern) throws Exception{
        RecruitmentPlan plan = recruitmentPlanService.findById(intern.getRecruitmentPlan().getId()).get();
        intern.setRecruitmentPlan(plan);

        if (!isFullIntern(intern.getRecruitmentPlan().getId())) {
            iInternRepository.save(intern);
        }else {
            throw new Exception("số lượng của kế hoạch này đã đủ");
        }
    }

    public boolean isFullIntern(Long recruitmentPlanId){
        boolean isFull = true;

        int totalInternNeed = recruitmentPlanDetailService.getTotalIntern(recruitmentPlanId);
        int internPlanHave = iInternRepository.countByRecruitmentPlanId(recruitmentPlanId);

        if (internPlanHave < totalInternNeed) {
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

}
