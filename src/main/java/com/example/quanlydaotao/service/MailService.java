package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.MailStructure;

public interface MailService {
    void sendMail(String mail, MailStructure mailStructure);
    Boolean sendMailHtml(String mail, MailStructure mailStructure);
}
