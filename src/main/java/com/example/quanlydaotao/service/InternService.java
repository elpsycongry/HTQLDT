package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InternService {
    List<InternProfile> getListIntern();
    List<InternScore> getListInternScore();
    InternProfile getInternProfile(Long internID);
//    List<InternScore> getInternScore(User user);
    List<InternSubject> getSubjects();
    Iterable<InternDTO> getAllInter();
    Iterable<InternDTO> findListInterWithNameInternAndTrainingState(String keyword, String trainingState);
    Iterable<InternDTO> findListInterWithNameInter(String keyword, Iterable<InternDTO> internDTOIterable);
    Iterable<InternDTO> findListInterWithTrainingState(String trainingState, Iterable<InternDTO> internDTOIterable);

    Page<InternDTO> convertToPage(List<InternDTO> internDTOList, Pageable pageable);

    List<Object[]> getAllByUserId(Long id);

    InternProfile save(InternProfile internProfile);
    List<InternScore> getListInternScoreByUserID(Long userID);

    InternSubject findInternSubjectByName(String name);

    Optional<InternScore> getInternScoreByUserAndSubjectAndType(User user, InternSubject subject, String type);

    Optional<InternProfile> getInternProfileByUserID(Long userId);

    void saveInternScore(InternScore internScore);

    List<SubjectComment> getListSubjectCommentByUserID(Long userID);

    Optional<SubjectComment> getSubjectCommentByID(Long idComment);

    void saveSubjectComment(SubjectComment subjectComment);
    void saveInterProfile(InternProfile internProfile);
}
