package com.example.quanlydaotao.repository;
import com.example.quanlydaotao.model.UserRecruitmentAction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRecruitmentActionRepository extends CrudRepository<UserRecruitmentAction, Long> {
    Optional<UserRecruitmentAction> findByUserId(long userId);
    Optional<UserRecruitmentAction> findByRecruitmentRequestId(long id);

}


