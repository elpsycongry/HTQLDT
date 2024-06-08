package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.Role;
import com.example.quanlydaotao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User save(User user);

    Iterable<User> findAll();

    Page<User> findAllUserWithRoles(Pageable pageable);

//    Page<User> findAllByNameOrEmail(Pageable pageable, String keyword);
//
//    Page<User> findUsersByRoles(Pageable pageable, Role role);
    Iterable<User> findAllUserWithRoles();
    Iterable<User> findAllByNameOrEmail(String keyword);

    Iterable<User> findUsersByRoles(Role role);
    Iterable<User> findUsersByStateAndStatus(String state, Iterable<User> users);
    Iterable<User> filterWithFields(String keyword, Long role_id, String state);

    Page<User> convertToPage(Iterable<User> users, Pageable pageable);
    User findByUsername(String username);

    Iterable<User> remoteRoleAdminDisplay(Iterable<User> users);

    User getCurrentUser();

    Optional<User> findById(Long id);

    UserDetails loadUserById(Long id);

    Page<User> getAllUsers(Pageable pageable);

    boolean checkLogin(User user);

    boolean checkPhoneExists(String phone);

    boolean checkAddPhoneExists(String phone);

    boolean checkEmailExists(String email);
    Optional<User> findByNameOrPhoneOrAndEmail(String name, String phone, String email);

}
