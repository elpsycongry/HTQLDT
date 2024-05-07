package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.RecruitmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long> {

}
