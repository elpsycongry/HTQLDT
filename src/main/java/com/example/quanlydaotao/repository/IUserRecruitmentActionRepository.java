package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRecruitmentActionRepository extends JpaRepository<UserRecruitmentAction, Long> {
    Optional<UserRecruitmentAction> findByUserId(long userId);
}
