package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.Users;
import com.example.quanlydaotao.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private IUserRepository userRepository;

    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }
}
