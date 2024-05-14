package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.RecruitmentPlan;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentPlanSpec implements Specification<RecruitmentPlan> {
    private RecruitmentPlanDTO recruitmentPlanDTO;
    public RecruitmentPlanSpec(RecruitmentPlanDTO recruitmentPlanDTO) {
        this.recruitmentPlanDTO = recruitmentPlanDTO;
    }
    @Override
    public Predicate toPredicate(Root<RecruitmentPlan> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (recruitmentPlanDTO.getName() != null)
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + recruitmentPlanDTO.getName() + "%"));
        if (recruitmentPlanDTO.getStatus() != null)
            predicates.add(criteriaBuilder.like(root.get("status"), "%" + recruitmentPlanDTO.getStatus() + "%"));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
