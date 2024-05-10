package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


import java.util.Optional;
@Repository
public interface IRecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long> {
    @Query("select rr.id,rr.name,rr.dateStart,rr.status from RecruitmentRequest rr ")
    Iterable<RecruitmentRequest> getAll();

    @Query("SELECT r.id, r.dateStart, r.dateEnd, r.name, r.reason, r.division, r.status FROM RecruitmentRequest r WHERE r.id =:id")
    Object[] findNonForeignKeyFields(@Param("id") Long id);
    @Query("DELETE FROM RecruitmentRequest r WHERE r.id =:value")
    void deleteAllExceptLinked(@Param("value") long id);
    @Query("select rr from RecruitmentRequest rr " +
            "inner join UserRecruitmentAction ura ON rr.id = ura.recruitmentRequest.id " +
            "where rr.name LIKE %:value%")
    Iterable<Object[]> findRecruitmentRequestsByNameContaining(@Param("value") String name);
    @Query("select rr from RecruitmentRequest rr " +
            "inner join UserRecruitmentAction ura on rr.id = ura.recruitmentRequest.id ")
    Iterable<Object[]> finAllRR();
    @Query("select rr from RecruitmentRequest rr " +
            "inner join UserRecruitmentAction ura ON rr.id = ura.recruitmentRequest.id " +
            "where rr.status =:value")
    Iterable<Object[]> statusFilter(@Param("value") String name);
    @Query("select rr from RecruitmentRequest rr " +
            "inner join UserRecruitmentAction ura ON rr.id = ura.recruitmentRequest.id ")
    Iterable<Object[]> statusList();


}
