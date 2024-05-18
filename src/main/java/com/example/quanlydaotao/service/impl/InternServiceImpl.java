package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.InternSubject;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.repository.InternProfileRepository;
import com.example.quanlydaotao.repository.InternScoreRepository;
import com.example.quanlydaotao.repository.InternSubjectRepository;
import com.example.quanlydaotao.repository.UserRepository;
import com.example.quanlydaotao.service.InternService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternServiceImpl implements InternService {

    private static final Logger log = LoggerFactory.getLogger(InternServiceImpl.class);
    @Autowired
    private InternProfileRepository internProfileRepository;

    @Autowired
    private InternScoreRepository internScoreRepository;

    @Autowired
    private InternSubjectRepository internSubjectRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<InternProfile> getListIntern() {
        return internProfileRepository.findAll();
    }
    @Override
    public List<InternScore> getListInternScore() {
        return internScoreRepository.findAll();
    }

    @Override
    public InternProfile getInternProfile(Long userID) {
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

    @Override
    public List<Object[]> getAllByUserId(Long id) {
        return internScoreRepository.getAllByUserId(id);
    }

    @Override
    public InternProfile save(InternProfile internProfile) {
        return internProfileRepository.save(internProfile);
    }

    @Override
    public List<InternScore> getListInternScoreByUserID(Long userID) {
        return internScoreRepository.getAllByUser(userRepository.findById(userID).get());
    }

    @Override
    public InternSubject findInternSubjectByName(String name) {
        return internSubjectRepository.findByName(name).get();
    }

    @Override
    public Optional<InternScore> getInternScoreByUserAndSubjectAndType(User user, InternSubject subject,String type) {
        return internScoreRepository.findByUserAndInternSubjectAndType(user , subject, type);
    }

    @Override
    public Optional<InternProfile> getInternProfileByUserID(Long userId) {

        return internProfileRepository.findByUser(userRepository.findById(userId).get());
    }

    @Override
    public void saveInternScore(InternScore internScore) {
         internScoreRepository.save(internScore);
    }


}
