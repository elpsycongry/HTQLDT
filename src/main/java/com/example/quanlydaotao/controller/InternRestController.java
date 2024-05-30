package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.IRecruitmentPlanRepository;
import com.example.quanlydaotao.service.IInternService;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.dto.SubjectDTO;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.UserService;
import com.example.quanlydaotao.service.impl.InternServiceImpl;
import com.example.quanlydaotao.service.impl.RecruitmentPlanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/interns")
@CrossOrigin("*")
public class InternRestController {
    @Autowired
    private InternService internService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecruitmentPlanService recruitmentPlanService;

    @GetMapping
    public ResponseEntity<?> getAllIntern(){
        return new ResponseEntity<>(internService.getListIntern(), HttpStatus.OK);
    }

    @GetMapping("/score")
    public ResponseEntity<?> getAllInternScore(){
        return new ResponseEntity<>(internService.getListInternScore(), HttpStatus.OK);
    }
    @GetMapping("/subject")
    public ResponseEntity<?> getAllInternSubject(){
        return new ResponseEntity<>(internService.getSubjects(), HttpStatus.OK);
    }

    @GetMapping("/recruitment_plan")
    public ResponseEntity<?> getAllRecruitmentPlan(){
        return new ResponseEntity<>(recruitmentPlanService.getAllRecruitmentPlan(), HttpStatus.OK);
    }

    @GetMapping("/findIntern")
    public ResponseEntity<Page<InternDTO>> getAllInterWithNameOrTrainingState(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Iterable<InternDTO> internDTOIterable = internService.getAllInter();
        Page<InternDTO> internDTOPage = internService.convertToPage((List<InternDTO>) internDTOIterable, pageable);
        return new ResponseEntity<>(internDTOPage, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InternDTO>> findListInterWithNameOrTrainingState(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "trainingState") String trainingState) {
        Pageable pageable = PageRequest.of(page, size);
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState(keyword, trainingState);
        Page<InternDTO> internDTOPage = internService.convertToPage((List<InternDTO>) internDTOIterable, pageable);
        return new ResponseEntity<>(internDTOPage, HttpStatus.OK);
    }

    @GetMapping("/searchValue")
    public ResponseEntity<Page<InternDTO>> findListInterWithNameOrTrainingStateOrRecruitmentPlan(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "trainingState") String trainingState,
            @RequestParam(name = "recruitmentPlan") String idRecruitmentPlan) {
        Pageable pageable = PageRequest.of(page, size);
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingStateAndRecruitmentPlan(keyword, trainingState, idRecruitmentPlan);
        Page<InternDTO> internDTOPage = internService.convertToPage((List<InternDTO>) internDTOIterable, pageable);
        return new ResponseEntity<>(internDTOPage, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<?> getInternDetailById(@RequestParam(value = "id", required = false) Long id) {
        return getInternDetail(id);
    }


    @PutMapping
    public ResponseEntity<?> editInternDetail(@RequestBody Map<String, Object> payload) {

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
        Intern intern = internService.findById(internProfile.getIntern().getId()).orElseThrow(() -> new RuntimeException("Intern not found"));
        List<Map<String, Object>> subjects = (List<Map<String, Object>>) payload.get("subjects");
        String[] TYPE_SCORE = {"theory", "practice", "attitude"};
        for (Map<String, Object> subj : subjects) {
            String subjectName = subj.get("name").toString();
            InternSubject internSubject = internService.findInternSubjectByName(subjectName);

            handleScore(intern, internSubject, TYPE_SCORE, subj.get("theoryScore"), subj.get("practiceScore"), subj.get("attitudeScore"));
        }

        // Lưu subject comment
        for (Map<String, Object> subj : subjects) {
            String json = subj.get("comment").toString();
            int idIndex = json.indexOf("id=");
            int commaIndex = json.indexOf(",");
            int valueIndex = json.indexOf("value=");
            if (idIndex != -1) {
                // Lấy chuỗi từ vị trí của "id=" đến vị trí của dấu phẩy
                String idString = json.substring(idIndex + 3, commaIndex).trim();
                // Chuyển đổi chuỗi thành Long
                Long idComment = Long.valueOf(idString);

                int valueCommaIndex = json.indexOf(",", valueIndex);
                if (valueCommaIndex == -1) {
                    // Nếu không tìm thấy dấu phẩy, sử dụng dấu } thay thế
                    valueCommaIndex = json.indexOf("}", valueIndex);
                }
                // Lấy chuỗi từ vị trí của "value=" đến vị trí của dấu phẩy hoặc dấu }
                String valueComment = json.substring(valueIndex + 6, valueCommaIndex).trim();

                SubjectComment subjectComment = internService.getSubjectCommentByID(idComment).get();
                subjectComment.setValue(valueComment);
                internService.saveSubjectComment(subjectComment);

            } else {
                //
                int valueCommaIndex = json.indexOf("}", valueIndex);
                String valueComment = json.substring(valueIndex + 6, valueCommaIndex).trim();
                if (!valueComment.isEmpty() && !valueComment.equals("null")) { // Sử dụng phương thức equals() để so sánh chuỗi
                    SubjectComment subjectComment = new SubjectComment();
                    subjectComment.setValue(valueComment);
                    subjectComment.setIntern(intern);
                    subjectComment.setInternSubject(internService.findInternSubjectByName(subj.get("name").toString()));
                    internService.saveSubjectComment(subjectComment);
                }
            }
        }

        return getInternDetail(intern.getId());
    }

    private void handleScore(Intern intern, InternSubject internSubject, String[] types, Object theoryScore, Object practiceScore, Object attitudeScore) {
        InternScore internScore;
        for (String type : types) {
            try {
                internScore = internService.getInternScoreByInternAndSubjectAndType(intern, internSubject, type).get();
            } catch (NoSuchElementException exception) {
                internScore = new InternScore();
                internScore.setIntern(intern);
                internScore.setInternSubject(internSubject);
                internScore.setType(type);
            }
            switch (type){
                case "theory":
                    if (theoryScore != null){
                        internScore.setValue(theoryScore.toString());
                    }
                    break;
                case "practice":
                    if (practiceScore != null){
                        internScore.setValue(practiceScore.toString());
                    }
                    break;
                case "attitude":
                    if (attitudeScore != null){
                        internScore.setValue(attitudeScore.toString());
                    }
            }
            internService.saveInternScore(internScore);
        }
    }

    public ResponseEntity<?> getInternDetail(Long id) {
        InternProfile profile = internService.getInternProfileByInternID(id).get();
        List<SubjectComment> comments = internService.getListSubjectCommentByInternID(id);
        List<Object[]> objects = internService.getAllByInternId(id);
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
        map.put("name", profile.getIntern().getName());
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