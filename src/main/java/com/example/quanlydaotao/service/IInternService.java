package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.Intern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IInternService {
    void createIntern(Intern intern);
    Page<Intern> showIntern(Pageable pageable);
    void updateIntern(Intern intern);
    Optional<Intern> getIntern(long id);
}
