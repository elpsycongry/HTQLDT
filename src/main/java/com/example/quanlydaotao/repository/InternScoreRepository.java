package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.model.InternSubject;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import java.util.List;

public interface InternScoreRepository extends JpaRepository<InternScore, Long> {
    List<InternScore> findAllByUser(User user);
    List<InternScore> getInternScoresByUser(User user);

    @Query("select i.value, i.type, i.internSubject.name from InternScore i where i.user.id = :id")
    List<Object[]> getAllByUserId(@Param("id") Long id);

    List<InternScore> getAllByUser(User user);

    Optional<InternScore> findByUserAndInternSubjectAndType(User user, InternSubject internSubject, String type);

}
