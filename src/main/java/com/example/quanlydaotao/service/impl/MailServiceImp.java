package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.MailStructure;
import com.example.quanlydaotao.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class MailServiceImp implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine ;

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
            message.setText(htmlText,true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

}
