package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.Intern;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InternSpec implements Specification<Intern> {

    private InternSearchDTO internSearchDTO;

    public InternSpec(InternSearchDTO internSearchDTO) {
        this.internSearchDTO = internSearchDTO;
    }

    @Override
    public Predicate toPredicate(Root<Intern> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (internSearchDTO.getNameOrEmail() != null) {
            String keyword = "%" + internSearchDTO.getNameOrEmail() + "%";
            Predicate namePredicate = criteriaBuilder.like(root.get("name"), keyword);
            Predicate emailPredicate = criteriaBuilder.like(root.get("email"), keyword);
            predicates.add(criteriaBuilder.or(namePredicate, emailPredicate));
        }
        if (internSearchDTO.getStatus() != null) {
            predicates.add(criteriaBuilder.like(root.get("status"), "%" + internSearchDTO.getStatus() + "%"));
        }
        if (internSearchDTO.getRecruitmentPlan() != null && internSearchDTO.getRecruitmentPlan().getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("recruitmentPlan").get("name"), "%" + internSearchDTO.getRecruitmentPlan().getName() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
