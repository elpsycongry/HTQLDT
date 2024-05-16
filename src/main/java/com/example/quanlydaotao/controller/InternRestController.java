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

import java.util.List;

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
    public ResponseEntity<Page<InternDTO>> getAllInterWithNameOrTrainingState(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Iterable<InternDTO> internDTOIterable = internService.getAllInter();
        Page<InternDTO> internDTOPage = internService.convertToPage((List<InternDTO>) internDTOIterable, pageable);
        return new ResponseEntity<>(internDTOPage, HttpStatus.OK);
    }


}
