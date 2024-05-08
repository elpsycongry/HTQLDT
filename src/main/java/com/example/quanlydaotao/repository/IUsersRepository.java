package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<Users, Long> {

}
