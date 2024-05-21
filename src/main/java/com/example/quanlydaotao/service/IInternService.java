package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.dto.InternSearchDTO;
import com.example.quanlydaotao.dto.PaginateRequest;
import com.example.quanlydaotao.dto.RecruitmentPlanDTO;
import com.example.quanlydaotao.model.Intern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IInternService {
    void createIntern(Intern intern);
    Page<Intern> showIntern(Pageable pageable);
    void updateIntern(Intern intern);
    Optional<Intern> getIntern(long id);
    Page<Intern> findAllByNameOrEmail(PaginateRequest paginateRequest, InternSearchDTO internSearchDTO);
}
