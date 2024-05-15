package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.InternDTO;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.impl.InternServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interns")
@CrossOrigin("*")
public class InternRestController {
    @Autowired
    private InternService internService;
    @GetMapping
    public ResponseEntity<?> getAllIntern(){
        return new ResponseEntity<>(internService.getListIntern(), HttpStatus.CREATED);
    }
    @GetMapping("/score")
    public ResponseEntity<?> getAllInternScore(){
        return new ResponseEntity<>(internService.getListInternScore(), HttpStatus.CREATED);
    }
    @GetMapping("/subject")
    public ResponseEntity<?> getAllInternSubject(){
        return new ResponseEntity<>(internService.getSubjects(), HttpStatus.CREATED);
    }

    @GetMapping("/findIntern")
    public ResponseEntity<Iterable<InternDTO>> getAllInterWithNameOrTrainingState() {
        Iterable<InternDTO> internDTOIterable = internService.getAllInterWithNameOrTrainingState();
        return new ResponseEntity<>(internDTOIterable, HttpStatus.OK);
    }

    @GetMapping("/findScore")
    public ResponseEntity<Iterable<InternScore>> getScore() {
        return new ResponseEntity<>(internService.findAllByUser(), HttpStatus.CREATED);
    }

}
