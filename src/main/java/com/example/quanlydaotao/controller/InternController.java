package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.service.impl.InternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interns")
public class InternController {
    @Autowired
    private InternService internService;
    @GetMapping("")
    public ResponseEntity<Page<Intern>> getIntern(@PageableDefault(5) Pageable pageable) {
        Page<Intern>internPage = internService.showIntern(pageable);
        return new ResponseEntity<>(internPage, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Intern> getInternById(@PathVariable long id) {
        return new ResponseEntity<>(internService.getIntern(id).get(),HttpStatus.OK);
    }
    @PutMapping("")
    public ResponseEntity<Intern> updateIntern(@RequestBody Intern intern) {
        internService.updateIntern(intern);
        return new ResponseEntity<>(intern,HttpStatus.OK);
    }
}
