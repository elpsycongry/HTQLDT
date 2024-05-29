package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.MailStructure;
import com.example.quanlydaotao.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImp implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendMail(String mail, MailStructure mailStructure) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail);
            message.setSubject(mailStructure.getSubject());
            message.setText(mailStructure.getText());
            mailSender.send(message);
            System.out.println("sent");
    }
}
