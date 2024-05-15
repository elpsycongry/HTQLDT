package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.InternSubject;
import com.example.quanlydaotao.model.User;

import java.util.List;

public interface InternService {
    List<InternProfile> getListIntern();
    List<InternScore> getListInternScore();
    InternProfile getInternProfileByUser(Long userID);
    List<InternScore> getInternScore(User user);
    List<InternSubject> getSubjects();
}
