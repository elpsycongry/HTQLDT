package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.model.SubjectComment;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface SubjectCommentRepo extends JpaRepository<SubjectComment, Long> {
    List<SubjectComment> findAllByIntern(Intern intern);
}
