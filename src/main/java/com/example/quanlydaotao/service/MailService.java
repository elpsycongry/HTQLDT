package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.MailStructure;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface MailService {
    void sendMail(String mail, MailStructure mailStructure);
    Boolean sendMailHtml(String mail, MailStructure mailStructure);
    void sendEmailWithTable(String to, String subject, String htmlTable);
    String createHtmlTable()throws MessagingException;
}
