package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentPlan;
import com.example.quanlydaotao.model.UserPlanAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserPlanActionRepository extends CrudRepository<UserPlanAction, Long> {
    Optional<UserPlanAction> findByRecruitmentPlanId(long id);
}
