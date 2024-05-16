package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.Role;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.model.UserPrinciple;
import com.example.quanlydaotao.repository.UserRepository;
import com.example.quanlydaotao.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleServiceImpl roleService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        if (this.checkLogin(user)) {
            return UserPrinciple.build(user);
        }
        boolean enable = false;
        boolean accountNonExpired = false;
        boolean credentialsNonExpired = false;
        boolean accountNonLocked = false;
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
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
    public Page<User> findAllUserWithRoles(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Iterable<User> remoteRoleAdminDisplay(Iterable<User> users) {
        List<User> userList = (List<User>) users;
        userList.removeIf(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")));
        users = userList;
        return users;
    }

    @Override
    public Iterable<User> findAllUserWithRoles() {
        Iterable<User> userIterable = userRepository.findAll();
        userIterable = remoteRoleAdminDisplay(userIterable);
        return userIterable;
    }

    @Override
    public Iterable<User> findAllByNameOrEmail(String keyword) {
        Iterable<User> userIterable = userRepository.findAllUserByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        userIterable = remoteRoleAdminDisplay(userIterable);
        return userIterable;
    }


    @Override
    public Iterable<User> findUsersByRoles(Role role) {
        Iterable<User> userIterable = userRepository.findUsersByRoles(role);
        userIterable = remoteRoleAdminDisplay(userIterable);
        return userIterable;
    }


    @Override
    public Iterable<User> filterWithFields(String keyword, Long role_id) {
        if (keyword.isEmpty()) {
            return remoteRoleAdminDisplay(findUsersByRoles(roleService.findById(role_id).orElseThrow()));
        }

        if (keyword.isEmpty() && role_id == 0) {
            return remoteRoleAdminDisplay(findAll());
        }

        Iterable<User> users = findAllByNameOrEmail(keyword);

        if (role_id == null) {
            return remoteRoleAdminDisplay(users);
        }

        Optional<Role> optionalRole = roleService.findById(role_id);

        return optionalRole
                .map(role -> remoteRoleAdminDisplay(StreamSupport.stream(users.spliterator(), false)
                        .filter(user -> user.getRoles().contains(role))
                        .collect(Collectors.toList())))
                .orElseGet(() -> remoteRoleAdminDisplay(Collections.emptyList()));
    }

    @Override
    public Page<User> convertToPage(Iterable<User> users, Pageable pageable) {
        List<User> userList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), userList.size());

        return new PageImpl<>(userList.subList(start, end), pageable, userList.size());
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
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

    public boolean checkPhoneExists(String phone) {
        long numberOfPhone = userRepository.countByPhone(phone);
        return numberOfPhone >= 2;
    }
}