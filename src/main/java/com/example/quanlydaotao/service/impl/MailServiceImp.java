package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.MailStructure;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.repository.IUserRecruitmentActionRepository;
import com.example.quanlydaotao.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MailServiceImp implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine ;
    @Autowired
    private UserRepository repository;

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

    @Override
    public void sendEmailWithTable(String to, String subject, String htmlTable){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            String htmlContent ="<html><body>"+htmlTable+"</body></html>";
            mimeMessageHelper.setText(htmlContent,true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

    public String createHtmlTable(){
        List<String> headers = Arrays.asList("Name","Email");
        Iterable<User> users = repository.findAll();
        List<List<String>> rows = new ArrayList<>();

        for (User user : users) {
            List<String> row = new ArrayList<>();
            row.add(user.getName());
            row.add(user.getEmail());
            rows.add(row);
        }

        StringBuilder htmlTable = new StringBuilder("<table border='1'><tr>");
        for (String header : headers) {
            htmlTable.append("<th>").append(header).append("</th>");
        }
        htmlTable.append("</tr>");
        for (List<?> row : rows) {
            htmlTable.append("<tr>");
            for (Object cell : row) {
                htmlTable.append("<td>").append(cell).append("</td>");
            }
            htmlTable.append("</tr>");
        }
        htmlTable.append("</table>");
        return htmlTable.toString();
    }
}
