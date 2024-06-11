package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Role;
import com.example.quanlydaotao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    Page<User> findAll(Pageable pageable);
    Iterable<User> findAllUserByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
    Iterable<User> findUsersByRoles(Role role);
    boolean existsByPhone(String phone);
    long countByPhone(String phone);
    List<User> findAllByRoles(Role role);
    Optional<User> findByNameOrPhoneOrAndEmail(String name, String phone, String email);
    Iterable<User> findUsersByStatusAndState(boolean status, boolean state);
    Iterable<User> findUsersByState(boolean state);
    Iterable<User> findUserByEmail(String email);
}
