package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.RecruitmentRequest;
import com.example.quanlydaotao.model.RecruitmentRequestDetail;
import com.example.quanlydaotao.service.impl.RecruitmentRequestDetailService;

import com.example.quanlydaotao.dto.RecruitmentFormDTO;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import com.example.quanlydaotao.service.impl.RecruitmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("/api/recruitmentRequests")
@CrossOrigin(value = "*")
public class RecruitmentRequestController {
    @Autowired
    private RecruitmentRequestService recruitmentRequestService;
    @Autowired
    private RecruitmentRequestDetailService recruitmentRequestDetailService;
    @Autowired
    private IRecruitmentRequestRepository recruitmentRequestRepository;

    @GetMapping()
    public ResponseEntity<Iterable<RecruitmentRequest>> getAllRecruitmentRequest() {
        Iterable<RecruitmentRequest> recruitmentRequestIterable = recruitmentRequestRepository.findAll();
        return new ResponseEntity<>(recruitmentRequestIterable, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RecruitmentRequest>> getRecruitmentRequestById(@PathVariable long id) {
        Optional<RecruitmentRequest> recruitmentRequest = recruitmentRequestService.findRecruitmentRequestById(id);
        return new ResponseEntity<>(recruitmentRequest, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Iterable<RecruitmentRequestDetail>> getRecruitmentRequestDetail(@PathVariable long id) {
        Iterable<RecruitmentRequestDetail> recruitmentRequestDetail = recruitmentRequestDetailService.findByRecruitmentId(id);
        return new ResponseEntity<>(recruitmentRequestDetail, HttpStatus.OK);
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecruitmentRequest(@PathVariable long id, @RequestBody RecruitmentFormDTO request) throws Exception {
        RecruitmentFormDTO recruitmentFormDTO = request;
        Iterable<RecruitmentRequest> recruitmentRequests = recruitmentRequestRepository.findAll();
        try {
            for (RecruitmentRequest recruitmentRequest : recruitmentRequests){
                if (recruitmentFormDTO.getRecruitmentRequest().getName().equals(recruitmentRequest.getName())) {
                    if (id != recruitmentRequest.getId()) {
                        return new ResponseEntity<>("cập nhật dữ liệu thất bại!",HttpStatus.EXPECTATION_FAILED);
                    }
                }
            }
            request.getRecruitmentRequest().setId(id);
            recruitmentRequestService.updateRecruitmentRequest(recruitmentFormDTO, id);

        } catch (Exception e) {
            return new ResponseEntity<>("cập nhật dữ liệu thất bại!",HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>("cập nhật dữ liệu thành công!",HttpStatus.OK);
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
