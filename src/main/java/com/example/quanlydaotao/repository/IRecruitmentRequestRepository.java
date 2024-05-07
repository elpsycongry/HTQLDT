package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IRecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long> {
    @Query("select rr.id,rr.name,rr.dateStart,rr.demandOriginator.user.name,rr.active,rr.status from RecruitmentRequest rr ")
    Iterable<RecruitmentRequest> getAll();
//    @Query("select rr from RecruitmentRequest rr where rr.id = :value")
//    Optional<RecruitmentRequest> getById(@Param("value") long id);
}
