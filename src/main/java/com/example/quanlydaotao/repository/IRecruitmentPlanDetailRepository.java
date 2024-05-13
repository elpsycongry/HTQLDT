package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentPlanDetail;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface IRecruitmentPlanDetailRepository extends CrudRepository<RecruitmentPlanDetail, Long> {
    Iterable<RecruitmentPlanDetail> findByRecruitmentPlanId(long id);
    void deleteAllByRecruitmentPlanId(long id);
}
