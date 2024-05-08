package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.repository.IUserRecruitmentActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRecruitmentActionService {
    @Autowired
    private IUserRecruitmentActionRepository userActionRepository;

    public void save(UserRecruitmentAction userAction) {
        userActionRepository.save(userAction);

    }
}
