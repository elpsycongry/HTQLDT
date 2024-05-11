package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.Role;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.model.UserPrinciple;
import com.example.quanlydaotao.repository.UserRepository;
import com.example.quanlydaotao.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        if (this.checkLogin(user)) {
            return UserPrinciple.build(user);
        }
        boolean enable = false;
        boolean accountNonExpired = false;
        boolean credentialsNonExpired = false;
        boolean accountNonLocked = false;
        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(), enable, accountNonExpired, credentialsNonExpired,
                accountNonLocked, null);
    }


    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Iterable<User> findAllUserWithRoles() {
        Iterable<User> userIterable = userRepository.findAll();
        return userIterable;
    }

    @Override
    public Iterable<User> findAllByNameOrEmail(String keyword) {
        Iterable<User> userIterable = userRepository.findAllUserByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        return userIterable;
    }

    @Override
    public Iterable<User> findUsersByRoles(Role role) {
        Iterable<User> userIterable = userRepository.findUsersByRoles(role);
        return userIterable;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User getCurrentUser() {
        User user;
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        user = this.findByUsername(userName);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NullPointerException();
        }
        return UserPrinciple.build(user.get());
    }

    @Override
    public boolean checkLogin(User user) {
        Iterable<User> users = this.findAll();
        boolean isCorrectUser = false;
        for (User currentUser : users) {
            if (currentUser.getName().equals(user.getName()) && user.getPassword().equals(currentUser.getPassword())) {
                isCorrectUser = true;
                break;
            }
        }
        return isCorrectUser;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}