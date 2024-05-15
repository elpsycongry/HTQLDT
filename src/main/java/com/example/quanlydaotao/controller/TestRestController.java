package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.SubjectDTO;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternSubject;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class TestRestController {
    @Autowired
    private InternService internService;

    @GetMapping("/api/test/")
    public ResponseEntity<?> getAllIntern(@RequestParam(value = "id", required = false) Long id) {
        InternProfile user = internService.getInternProfile(id);
        List<Object[]> objects = internService.getAllByUserId(id);
        List<InternSubject> subjects = internService.getSubjects();

        Map<String, Object> map = new HashMap<String, Object>();
        List<SubjectDTO> subjectDTOs = new ArrayList<>();

        for (InternSubject subject : subjects) {
            addSubjectIfNotExist(subjectDTOs, subject.getName());
        }

        // convert subject sang định dạng fe
        for (Object[] obj : objects) {
            String name = (String) obj[2];
            String type = (String) obj[1];
            String score = (String) obj[0];

        // Nếu môn học
            SubjectDTO subjectDTO = addSubjectIfNotExist(subjectDTOs, name);

            if (type.equals("theory")) {
                subjectDTO.setTheoryScore(score);
            } else if (type.equals("practice")) {
                subjectDTO.setPracticeScore(score);
            } else if (type.equals("attitude")) {
                subjectDTO.setAttitudeScore(score);
            }
        }

        map.put("subjects", subjectDTOs);
        map.put("name", user.getUser().getName());
        map.put("startDate", user.getStartDate());
        map.put("endDate", user.getEndDate());
        map.put("trainingState", user.getTrainingState());
        map.put("isPass", user.getIsPass());
        map.put("scoreInTeam", user.getScoreInTeam());


        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    private static SubjectDTO addSubjectIfNotExist(List<SubjectDTO> subjectDTOs, String name) {

        for (SubjectDTO subjectDTO : subjectDTOs) {
            if (subjectDTO.getName().equals(name)) {
                return subjectDTO;
            }
        }

        SubjectDTO newSubjectDTO = new SubjectDTO();
        newSubjectDTO.setName(name);
        subjectDTOs.add(newSubjectDTO);
        return newSubjectDTO;
    }
}
