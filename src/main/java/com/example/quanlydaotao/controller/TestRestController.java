package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.SubjectDTO;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternSubject;
import com.example.quanlydaotao.model.SubjectComment;
import com.example.quanlydaotao.repository.InternScoreRepository;
import com.example.quanlydaotao.repository.InternSubjectRepository;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin("*")
@RestController
public class TestRestController {
    @Autowired
    private InternService internService;

    @GetMapping("/api/test/")
    public ResponseEntity<?> getAllIntern(@RequestParam(value = "id", required = false) Long id) {
        return getInternDetail(id);
    }

    public ResponseEntity<?> getInternDetail(Long id) {
        InternProfile profile = internService.getInternProfileByUserID(id).get();
        List<SubjectComment> comments = internService.getListSubjectCommentByUserID(id);
        List<Object[]> objects = internService.getAllByUserId(id);
        List<InternSubject> subjects = internService.getSubjects();

        Map<String, Object> map = new HashMap<String, Object>();
        List<SubjectDTO> subjectDTOs = new ArrayList<>();

        for (InternSubject subject : subjects) {
            addSubjectIfNotExist(subjectDTOs, subject.getName(), comments);
        }

        // convert subject sang định dạng fe
        for (Object[] obj : objects) {
            String name = (String) obj[2];
            String type = (String) obj[1];
            String score = (String) obj[0];

            // Nếu môn học
            SubjectDTO subjectDTO = addSubjectIfNotExist(subjectDTOs, name, comments);

            if (type.equals("theory")) {
                subjectDTO.setTheoryScore(score);
            } else if (type.equals("practice")) {
                subjectDTO.setPracticeScore(score);
            } else if (type.equals("attitude")) {
                subjectDTO.setAttitudeScore(score);
            }
        }
        map.put("internID", profile.getId());
        map.put("subjects", subjectDTOs);
        map.put("name", profile.getUser().getName());
        map.put("startDate", profile.getStartDate());
        map.put("endDate", profile.getEndDate());
        map.put("trainingState", profile.getTrainingState());
        map.put("isPass", profile.getIsPass());
        map.put("scoreInTeam", profile.getScoreInTeam());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private static SubjectDTO addSubjectIfNotExist(List<SubjectDTO> subjectDTOs, String name, List<SubjectComment> comments) {

        for (SubjectDTO subjectDTO : subjectDTOs) {
                if (subjectDTO.getName().equals(name)) {
                    return subjectDTO;
            }
        }

        SubjectDTO newSubjectDTO = new SubjectDTO();
        newSubjectDTO.setName(name);
        for (SubjectComment comment : comments) {
            if (Objects.equals(newSubjectDTO.getName(), comment.getInternSubject().getName())){
                newSubjectDTO.setComment(comment);
            }
        }

        subjectDTOs.add(newSubjectDTO);
        return newSubjectDTO;
    }
}
