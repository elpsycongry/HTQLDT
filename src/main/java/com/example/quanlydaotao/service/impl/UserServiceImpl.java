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
import org.springframework.data.domain.Pageable;
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
//        userList.removeIf(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_HR")));
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
    public Iterable<User> findUsersByState(String state) {
        switch (state) {
            case "await":
                return remoteRoleAdminDisplay(userRepository.findAllByState(false));
            case "action":
                return remoteRoleAdminDisplay(userRepository.findAllByState(true));
            case "block":
                return remoteRoleAdminDisplay(userRepository.findAllByStatus(false));
            default:
                return remoteRoleAdminDisplay(userRepository.findAll());
        }
    }


    @Override
    public Iterable<User> filterWithFields(String keyword, Long role_id, String state) {
        if (keyword.isEmpty()) {
            return remoteRoleAdminDisplay(findUsersByRoles(roleService.findById(role_id).orElseThrow()));
        }

        if (keyword.isEmpty() && role_id == 0) {
            return remoteRoleAdminDisplay(findUsersByState(state));
        }

        Iterable<User> users = findAllByNameOrEmail(keyword);

        if (role_id == null) {
            return remoteRoleAdminDisplay(StreamSupport.stream(users.spliterator(), false)
                    .filter(user -> matchState(user, state))
                    .collect(Collectors.toList()));
        }

        Optional<Role> optionalRole = roleService.findById(role_id);

        return optionalRole
                .map(role -> remoteRoleAdminDisplay(StreamSupport.stream(users.spliterator(), false)
                        .filter(user -> user.getRoles().contains(role) && matchState(user, state))
                        .collect(Collectors.toList())))
                .orElseGet(() -> remoteRoleAdminDisplay(Collections.emptyList()));
    }

    private boolean matchState(User user, String state) {
        if (state == null || state.isEmpty()) {
            return true;
        }
        switch (state) {
            case "await":
                return !user.isState();
            case "action":
                return user.isState();
            case "block":
                return !user.isStatus();
            default:
                return true;
        }
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

    @Override
    public boolean checkPhoneExists(String phone) {
        long numberOfPhone = userRepository.countByPhone(phone);
        return numberOfPhone >= 2;
    }

    @Override
    public boolean checkAddPhoneExists(String phone) {
        long numberOfPhone = userRepository.countByPhone(phone);
        return numberOfPhone >= 1;
    }

    @Override
    public boolean checkEmailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public Optional<User> findByNameOrPhoneOrAndEmail(String name, String phone, String email) {
        return userRepository.findByNameOrPhoneOrAndEmail(name, phone, email);
    }
}