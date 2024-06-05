package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.MailStructure;
import com.example.quanlydaotao.model.RecruitmentPlanDetail;
import com.example.quanlydaotao.model.Role;
import com.example.quanlydaotao.model.SendEmail;
import com.example.quanlydaotao.repository.IRecruitmentPlanDetailRepository;
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
    private UserRepository userRepository;
    @Autowired
    private IRecruitmentPlanDetailRepository iRecruitmentPlanDetailRepository;
    @Autowired
    private IRecruitmentRequestRepository iRecruitmentRequestRepository;

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
    public SendEmail sendEmail() {
        SendEmail sendEmail = new SendEmail();
        sendEmail.setUsers(userRepository.findAllUserRole());
        sendEmail.setRecruitmentRequests(iRecruitmentRequestRepository.findAll());
        sendEmail.setRecruitmentPlanDetails((List<RecruitmentPlanDetail>) iRecruitmentPlanDetailRepository.findAll());
        return sendEmail;
    }
}
