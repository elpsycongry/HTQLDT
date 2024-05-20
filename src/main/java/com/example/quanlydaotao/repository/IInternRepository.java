package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Intern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInternRepository extends JpaRepository<Intern,Long> {
    Page<Intern> findAllByOrderByDateCreateDesc(Pageable pageable);
}
