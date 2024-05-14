package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.InternSubject;

import java.util.List;

public interface InternService {
    List<InternProfile> getListIntern();
    List<InternScore> getListInternScore();

    List<InternSubject> getSubjects();
}
