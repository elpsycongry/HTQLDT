package com.example.quanlydaotao.repository;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRecruitmentActionRepository extends CrudRepository<UserRecruitmentAction, Long> {
    Optional<UserRecruitmentAction> findByUserId(long userId);
    Optional<UserRecruitmentAction> findByRecruitmentRequestId(long id);

}