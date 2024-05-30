package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.SubjectComment;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectCommentRepo extends JpaRepository<SubjectComment, Long> {
    List<SubjectComment> findAllByUser(User user);
}
