package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.dto.InternSearchDTO;
import com.example.quanlydaotao.dto.PaginateRequest;
import com.example.quanlydaotao.model.Intern;
import com.example.quanlydaotao.model.RecruitmentPlan;
import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.service.impl.InternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<Intern>> getIntern(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Intern> internPage = internService.showIntern(pageable);
        return new ResponseEntity<>(internPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intern> getInternById(@PathVariable long id) {
        return new ResponseEntity<>(internService.getIntern(id).get(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity addIntern(@RequestBody InternDTO dto) {
        try {
            internService.addIntern(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Thêm ứng viên thành công", HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Intern> updateIntern(@RequestBody Intern intern, @PathVariable long id) {
        intern.setId(id);
        internService.updateIntern(intern);
        return new ResponseEntity<>(intern, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Intern>> searchAllIntern(@RequestParam(value = "keyword", required = false) String keyword,
                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                        @RequestParam(value = "namePlan", required = false) String namePlan,
                                                        @RequestParam(value = "status", required = false) String status) {
        RecruitmentPlan recruitmentPlan = null;
        if (namePlan != null && namePlan.isEmpty()) {
            recruitmentPlan = new RecruitmentPlan();
            recruitmentPlan.setName(namePlan);
        }
        InternSearchDTO internDTO = new InternSearchDTO(keyword, status, recruitmentPlan);
        PaginateRequest paginateRequest = new PaginateRequest(page, size);
        Page<Intern> internPage = internService.findAllByNameOrEmail(paginateRequest, internDTO);
        return new ResponseEntity<>(internPage, HttpStatus.OK);
    }
}
