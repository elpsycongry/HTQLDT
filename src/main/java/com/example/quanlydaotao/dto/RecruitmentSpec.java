package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.RecruitmentRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentSpec implements Specification<RecruitmentRequest> {

    private RecruitmentSearchDTO recruitmentSearchDTO;
    public RecruitmentSpec(RecruitmentSearchDTO recruitmentSearchDTO) {
        this.recruitmentSearchDTO = recruitmentSearchDTO;
    }
    @Override
    public Predicate toPredicate(Root<RecruitmentRequest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (recruitmentSearchDTO.getName() != null)
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + recruitmentSearchDTO.getName() + "%"));
        if (recruitmentSearchDTO.getStatus() != null)
            predicates.add(criteriaBuilder.like(root.get("status"), "%" + recruitmentSearchDTO.getStatus() + "%"));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
