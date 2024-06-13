package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.UserRecruitmentAction;
import com.example.quanlydaotao.model.Users;
import com.example.quanlydaotao.repository.IUserRecruitmentActionRepository;
import com.example.quanlydaotao.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserRecruitmentActionRepository userRecruitmentActionRepository;
    @GetMapping("")
    public ResponseEntity<List<Users>> findAll() {
        Iterable<UserRecruitmentAction> userRecruitmentActions = userRecruitmentActionRepository.findAll();
        List<Users> list = new ArrayList<>();
        for (UserRecruitmentAction userRecruitmentAction : userRecruitmentActions) {
            Optional<Users> users = userRepository.findById(userRecruitmentAction.getUser().getId());
            Users item = users.get();
            list.add(item);
        }
       return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
