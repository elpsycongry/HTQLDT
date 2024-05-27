package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.UserPlanAction;
import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.repository.IUserPlanActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPlanActionService {
    @Autowired
    private IUserPlanActionRepository userPlanActionRepository;
    public void save(UserPlanAction userAction) {userPlanActionRepository.save(userAction);
    }

    public Optional<UserPlanAction> findByRecruitmentPlanId(long id) {
        return userPlanActionRepository.findByRecruitmentPlanId(id);
    }

    public void deleteById(Long id) {
        userPlanActionRepository.deleteById(id);
    }
}
