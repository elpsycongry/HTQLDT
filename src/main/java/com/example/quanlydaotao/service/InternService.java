package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.InternDTO;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.InternSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InternService {
    List<InternProfile> getListIntern();
    List<InternScore> getListInternScore();
    List<InternSubject> getSubjects();
    Iterable<InternDTO> getAllInter();
    Page<InternDTO> convertToPage(List<InternDTO> internDTOList, Pageable pageable);}
