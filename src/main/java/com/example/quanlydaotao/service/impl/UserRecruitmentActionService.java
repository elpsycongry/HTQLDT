package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.repository.IUserRecruitmentActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRecruitmentActionService {
    @Autowired
    private IUserRecruitmentActionRepository userActionRepository;

    public void save(UserRecruitmentAction userAction) {
        userActionRepository.save(userAction);
    }

    public Optional<UserRecruitmentAction> findByRecruitmentRequestId(long id) {
        return userActionRepository.findByRecruitmentRequestId(id);
    }

    public void delete(UserRecruitmentAction userRecruitmentAction) {
        userActionRepository.delete(userRecruitmentAction);
    }
}
