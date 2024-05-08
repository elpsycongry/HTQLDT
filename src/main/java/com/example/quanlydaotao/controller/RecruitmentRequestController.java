package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.dto.RecruitmentFormDTO;
import com.example.quanlydaotao.service.impl.RecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitmentRequests")
public class RecruitmentRequestController {
    @Autowired
    private RecruitmentRequestService recruitmentRequestService;

    @GetMapping()
    public ResponseEntity<Iterable<RecruitmentRequest>> getAllRecruitmentRequest() {
        Iterable<RecruitmentRequest> recruitmentRequestIterable = recruitmentRequestService.getAllRecruitmentRequests();
        return new ResponseEntity<>(recruitmentRequestIterable, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity createRecruitmentRequest(@RequestBody RecruitmentFormDTO recruitmentFormDTO) {
        RecruitmentFormDTO request = recruitmentFormDTO;
        try {
            recruitmentRequestService.createRecruitmentRequest(recruitmentFormDTO);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Tên của yêu cầu nhân sự đã tồn tại!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/users/{idUser}")
    public ResponseEntity updateRecruitmentStatus(@RequestBody RecruitmentFormDTO recruitmentFormDTO, @PathVariable("id") Long idRecruitment, @PathVariable Long idUser) {
        String action = recruitmentFormDTO.getRecruitmentRequest().getStatus();
        String reason = recruitmentFormDTO.getRecruitmentRequest().getReason();
        try {
            recruitmentRequestService.updateStatusRecruitment(idRecruitment, idUser, action,reason);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Đã từ chối", HttpStatus.OK);

    }
}
