package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.InternSubject;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.repository.InternProfileRepository;
import com.example.quanlydaotao.repository.InternScoreRepository;
import com.example.quanlydaotao.repository.InternSubjectRepository;
import com.example.quanlydaotao.service.InternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InternServiceImpl implements InternService {
    @Autowired
    private InternProfileRepository internProfileRepository;

    @Autowired
    private InternScoreRepository internScoreRepository;

    @Autowired
    private InternSubjectRepository internSubjectRepository;
    @Override
    public List<InternProfile> getListIntern() {
        return internProfileRepository.findAll();
    }
    @Override
    public List<InternScore> getListInternScore() {
        return internScoreRepository.findAll();
    }

    @Override
    public InternProfile getInternProfileByUser(Long userID) {
        return internProfileRepository.findById(userID).get();
    }

    @Override
    public List<InternScore> getInternScore(User user) {
        return internScoreRepository.getInternScoresByUser(user);
    }

    @Override
    public List<InternSubject> getSubjects() {
        return internSubjectRepository.findAll();
    }
}
