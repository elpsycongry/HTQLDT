package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.EmailDTO;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.IRecruitmentPlanDetailRepository;
import com.example.quanlydaotao.repository.IRecruitmentPlanRepository;
import com.example.quanlydaotao.repository.IRecruitmentRequestRepository;
import com.example.quanlydaotao.repository.UserRepository;
import com.example.quanlydaotao.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Arrays;
import java.util.List;

@Service
public class MailServiceImp implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private InternService internService;
    @Autowired
    private IRecruitmentPlanRepository recruitmentPlanService;
    @Autowired
    private RecruitmentPlanDetailService recruitmentPlanDetailService;
    @Autowired
    private RecruitmentRequestDetailService recruitmentRequestDetailService;
    @Autowired
    private RecruitmentRequestService recruitmentRequestService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendMail(String mail, MailStructure mailStructure) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject(mailStructure.getSubject());
        message.setText(mailStructure.getText());
        mailSender.send(message);
        System.out.println("sent");
    }

    @Override
    public Boolean sendMailHtml(String mail, MailStructure mailStructure) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Context context = new Context();
        context.setVariable("mail", mail);
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setTo(mail);
            String htmlText = templateEngine.process("email-template.html", context);
            message.setSubject("subject");
            message.setText(htmlText, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public EmailDTO getDataSendEmail() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setInternNeeds(Integer.parseInt(recruitmentPlanDetailService.getTotalInPersonalNeeds(Long.parseLong(String.valueOf(1)))));
        emailDTO.setInternNeedHandOver(Integer.parseInt(recruitmentPlanDetailService.getTotalOutPersonalNeeds(Long.parseLong(String.valueOf(1)))));
        emailDTO.setPassIntern(internService.internPass(1));
        emailDTO.setDateStart(String.valueOf(recruitmentRequestService.getDateStart(5)));
        emailDTO.setDateEnd(String.valueOf(recruitmentRequestService.getDateEnd(5)));
        emailDTO.setNamePersonalNeeds(recruitmentRequestService.getRecruitmentNameById(5));
        emailDTO.setLinkProgress((int) recruitmentPlanDetailService.getIdRecruitmentNeeds(2));
        emailDTO.setTotalIntern(recruitmentPlanDetailService.getTotalIntern(Long.parseLong(String.valueOf(5))));
        emailDTO.setPassIntern(internService.internPass(5));
        emailDTO.setTraining_intern(internService.trainingByPlan(5));
        emailDTO.setFail_intern(internService.internFail(5));
        return emailDTO;
    }

    public EmailDTO getEmailAndName (){
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setToName(userService.getNameUserByRole());
        emailDTO.setToEmail(userService.getEmailUserRole());
        return emailDTO;
    }
}
