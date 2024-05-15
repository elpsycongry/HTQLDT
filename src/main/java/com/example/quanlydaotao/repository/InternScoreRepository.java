package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InternScoreRepository extends JpaRepository<InternScore, Long> {
    public List<InternScore> getInternScoresByUser(User user);
    @Query("select i.value, i.type, i.internSubject.name from InternScore i")
    public List<Object[]> testGet();

}
