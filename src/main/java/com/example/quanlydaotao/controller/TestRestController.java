package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.repository.InternScoreRepository;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestRestController {
    @Autowired
    private InternService internService;
    @Autowired
    private UserService userService;
    @Autowired
    private InternScoreRepository internScoreRepository;
    @GetMapping
    public ResponseEntity<?> getAllIntern(){
        List<InternProfile> list = internService.getListIntern();
        InternProfile user = internService.getInternProfileByUser(1L);
        List<Object[]> objects = internScoreRepository.testGet();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", user.getUser().getName() );
        map.put("startDate", user.getStartDate() );
        map.put("endDate", user.getEndDate() );
        map.put("trainingState", user.getTrainingState() );
        map.put("isPass", user.getIsPass() );
        map.put("scoreInTeam", user.getScoreInTeam() );
        map.put("subjects", objects);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
