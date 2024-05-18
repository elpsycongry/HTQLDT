package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.dto.SubjectDTO;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.InternSubject;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.UserService;
import com.example.quanlydaotao.service.impl.InternServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interns")
@CrossOrigin("*")
public class InternRestController {
    @Autowired
    private InternService internService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllIntern(){
        return new ResponseEntity<>(internService.getListIntern(), HttpStatus.CREATED);
    }
    @GetMapping("/score")
    public ResponseEntity<?> getAllInternScore(){
        return new ResponseEntity<>(internService.getListInternScore(), HttpStatus.CREATED);
    }@GetMapping("/subject")
    public ResponseEntity<?> getAllInternSubject(){
        return new ResponseEntity<>(internService.getSubjects(), HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<?> getInternDetailById(@RequestParam(value = "id", required = false) Long id) {
       return getInternDetail(id);
    }















































    @PutMapping
    public ResponseEntity<?> editInternDetail(@RequestBody Map<String, Object> payload){

        InternProfile internProfile = internService.getInternProfile(Long.parseLong(payload.get("internID").toString()));
        // Xử lý startDate
        if (payload.get("startDate") != null) {
            internProfile.setStartDate(LocalDate.parse(payload.get("startDate").toString()));
        }

        // Xử lý endDate
        if (payload.get("endDate") != null) {
            internProfile.setEndDate(LocalDate.parse(payload.get("endDate").toString()));
        }

        // Xử lý trainingState
        if (payload.get("trainingState") != null) {
            internProfile.setTrainingState(payload.get("trainingState").toString());
        }

        // Xử lý isPass
        if (payload.get("isPass") != null) {
            internProfile.setIsPass(Boolean.valueOf(payload.get("isPass").toString()));
        }

        // Xử lý scoreInTeam
        if (payload.get("scoreInTeam") != null) {
            internProfile.setScoreInTeam(payload.get("scoreInTeam").toString());
        }
        internService.save(internProfile);
        // Lưu InternScore
        User user = userService.findById(internProfile.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));

        List<Map<String, Object>> subjects = (List<Map<String, Object>>) payload.get("subjects");

        for (Map<String, Object> subj : subjects) {
            String subjectName = subj.get("name").toString();
            InternSubject internSubject = internService.findInternSubjectByName(subjectName);


//            handleScore(user, internSubject, "theory", subj.get("theoryScore"));
//            handleScore(user, internSubject, "practice", subj.get("practiceScore"));
//            handleScore(user, internSubject, "attitude", subj.get("attitudeScore"));
        }

        return ResponseEntity.status(HttpStatus.OK).body("Intern detail edited successfully");
    }


    public ResponseEntity<?> getInternDetail(Long id){
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
        map.put("internID", user.getId());
        map.put("subjects", subjectDTOs);
        map.put("name", user.getUser().getName());
        map.put("startDate", user.getStartDate());
        map.put("endDate", user.getEndDate());
        map.put("trainingState", user.getTrainingState());
        map.put("isPass", user.getIsPass());
        map.put("scoreInTeam", user.getScoreInTeam());
        return new ResponseEntity<>(map, HttpStatus.OK);
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
