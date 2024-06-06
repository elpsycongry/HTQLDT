package com.example.quanlydaotao.service;

import com.example.quanlydaotao.dto.EmailDTO;
import com.example.quanlydaotao.model.MailStructure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MailService {
    void sendMail(String mail, MailStructure mailStructure);
    Boolean sendMailHtml(String mail, MailStructure mailStructure);
    EmailDTO getDataSendEmail();
    List<String> getEmailAndNameRoleDM();
}
