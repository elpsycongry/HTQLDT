package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.SubjectDTO;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.*;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.MailService;
import com.example.quanlydaotao.service.UserService;
import com.example.quanlydaotao.service.impl.MailServiceImp;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
public class TestRestController {
    @Autowired
    private MailService mailService;

    @GetMapping("/api/test/")
    public ResponseEntity<?> getAllIntern(@RequestParam(value = "id", required = false) Long id) {
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Subject");
        mailStructure.setText("Text");
        mailService.sendMailHtml("mimun0407@gmail.com", mailStructure);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/api/send/")
    public ResponseEntity<SendEmail> sendMail() {
        SendEmail sendEmail = mailService.sendEmail();
        return new ResponseEntity<>(sendEmail, HttpStatus.OK);
    }
}
