package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long> {


    @Query("SELECT r.id, r.dateStart, r.dateEnd, r.name, r.reason, r.division, r.status FROM RecruitmentRequest r WHERE r.id =:id")
    Object[] findNonForeignKeyFields(@Param("id") Long id);
}
