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
    List<InternScore> getInternScore(Intern intern);
    List<InternSubject> getSubjects();
    Iterable<InternDTO> getAllInter();
    Iterable<InternDTO> findListInterWithNameInternAndTrainingState(String keyword, String trainingState);
    Iterable<InternDTO> findListInterWithNameInternAndTrainingStateAndRecruitmentPlan(String keyword, String trainingState, String recruitmentPlan);
    Iterable<InternDTO> findListInterWithNameInter(String keyword, Iterable<InternDTO> internDTOIterable);
    Iterable<InternDTO> findListInterWithTrainingState(String trainingState, Iterable<InternDTO> internDTOIterable);
    Iterable<InternDTO> findListInterWithRecruitmentPlan(String recruitmentPlan, Iterable<InternDTO> internDTOIterable);

    Page<InternDTO> convertToPage(List<InternDTO> internDTOList, Pageable pageable);

    List<Object[]> getAllByInternId(Long id);

    InternProfile save(InternProfile internProfile);
    List<InternScore> getListInternScoreByInternID(Long internID);

    InternSubject findInternSubjectByName(String name);

    Optional<InternScore> getInternScoreByInternAndSubjectAndType(Intern intern, InternSubject subject, String type);

    Optional<InternProfile> getInternProfileByInternID(Long internID);

    void saveInternScore(InternScore internScore);

    List<SubjectComment> getListSubjectCommentByInternID(Long internID);

    Optional<SubjectComment> getSubjectCommentByID(Long idComment);

    void saveSubjectComment(SubjectComment subjectComment);
    Optional<Intern> findById(Long internID);

    String isFullIntern(long recruitmentPlanId);
    void checkNumberOfRecruitment();
}