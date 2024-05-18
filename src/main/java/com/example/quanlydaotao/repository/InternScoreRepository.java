package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InternScoreRepository extends JpaRepository<InternScore, Long> {
     List<InternScore> getInternScoresByUser(User user);
    @Query("select i.value, i.type, i.internSubject.name from InternScore i where i.user.id = :id")
     List<Object[]> getAllByUserId(@Param("id") Long id);

    List<InternScore> getAllByUser(User user);

}
