package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.InternSubject;
import com.example.quanlydaotao.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InternService {
    List<InternProfile> getListIntern();
    List<InternScore> getListInternScore();
    InternProfile getInternProfile(Long internID);
    List<InternScore> getInternScore(User user);
    List<InternSubject> getSubjects();
    List<Object[]> getAllByUserId(Long id);

    InternProfile save(InternProfile internProfile);
    List<InternScore> getListInternScoreByUserID(Long userID);

    InternSubject findInternSubjectByName(String name);
}
