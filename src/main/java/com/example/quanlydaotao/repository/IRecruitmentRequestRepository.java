package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IRecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long> , JpaSpecificationExecutor<RecruitmentRequest> {
    @Query("select rr.id,rr.name,rr.dateStart,rr.status from RecruitmentRequest rr ")
    Iterable<RecruitmentRequest> getAll();

    @Query("SELECT r.id, r.dateStart, r.dateEnd, r.name, r.reason, r.division, r.status FROM RecruitmentRequest r WHERE r.id =:id")
    Object[] findNonForeignKeyFields(@Param("id") Long id);
    @Query("DELETE FROM RecruitmentRequest r WHERE r.id =:value")
    void deleteAllExceptLinked(@Param("value") long id);
}
