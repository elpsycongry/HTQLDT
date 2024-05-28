package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import java.util.List;

public interface InternScoreRepository extends JpaRepository<InternScore, Long> {
    List<InternScore> findAllByIntern(Intern intern);
    List<InternScore> getInternScoreByIntern(Intern intern);

    @Query("select i.value, i.type, i.internSubject.name from InternScore i where i.intern.id = :id")
    List<Object[]> getAllByInternId(@Param("id") Long id);

    List<InternScore> getAllByIntern(Intern internFindByUserAndInternSubjectAndType);

    Optional<InternScore> findByInternAndInternSubjectAndType(Intern intern, InternSubject internSubject, String type);

}