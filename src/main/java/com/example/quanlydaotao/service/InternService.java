package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.InternDTO;
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
    Iterable<InternDTO> findListInterWithNameInternAndTrainingState(String keyword, String trainingState);
    Iterable<InternDTO> findListInterWithNameInter(String keyword, Iterable<InternDTO> internDTOIterable);
    Iterable<InternDTO> findListInterWithTrainingState(String trainingState, Iterable<InternDTO> internDTOIterable);

    Page<InternDTO> convertToPage(List<InternDTO> internDTOList, Pageable pageable);

}
