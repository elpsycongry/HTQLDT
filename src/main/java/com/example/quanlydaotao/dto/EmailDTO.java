package com.example.quanlydaotao.dto;

import java.util.ArrayList;
import java.util.List;

public class EmailDTO {
    List<EmailData> emailData = new ArrayList<>();

    public List<EmailData> getEmailData() {
        return emailData;
    }

    public void addNewEmailData(EmailData email) {
         this.emailData.add(email);
    }
}
