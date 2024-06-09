package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.EmailDTO;
import com.example.quanlydaotao.dto.EmailData;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.UserRepository;
import com.example.quanlydaotao.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MailServiceImp implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private InternService internService;
    @Autowired
    private RecruitmentPlanService planService;
    @Autowired
    private RecruitmentPlanDetailService planDetailService;
    @Autowired
    private RecruitmentRequestDetailService recruitmentRequestDetailService;
    @Autowired
    private RecruitmentRequestService requestService;
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
        List<String> DMRole = getEmailAndNameRoleDM();
        List<String> HRRole = getEmailAndNameRoleHR();

        List<String> emailAndNames = Stream.of(DMRole, HRRole)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<Long> requestIds = requestService.getAllIdRequestsNeedSendEmail();
        List<RecruitmentPlan> plans = planService.findPlansInListIdRequests(requestIds);
        EmailDTO emailDTO = new EmailDTO();

        for (RecruitmentPlan plan : plans) {
            for (String emailAndName : emailAndNames) {
                String[] emailAndNameArr = emailAndName.split(",");
                EmailData emailData = new EmailData();
                emailData.setLinkProgress(Math.toIntExact(plan.getRecruitmentRequest().getId()))
                        .setToEmail(emailAndNameArr[0])
                        .setToName(emailAndNameArr[1])`
                        .setNamePersonalNeeds(plan.getRecruitmentRequest().getName())
                        .setDateStart(plan.getRecruitmentRequest().getDateStart().toLocalDate().toString())
                        .setDateEnd(plan.getRecruitmentRequest().getDateEnd().toString())
                        .setInternNeeds(planDetailService.getTotalIntern(plan.getId()))
                        .setHandOverIntern(planDetailService.getTotalResult(plan.getId()))
                        .setTotalIntern(internService.applicantsByPlan(plan.getId()))
                        .setPassIntern(internService.applicantsPassByPlan(plan.getId()))
                        .setFailIntern(internService.applicantsFailByPlan(plan.getId()))
                        .setTrainingIntern(internService.trainingByPlan(plan.getId()))
                        .setNotTrainingIntern(internService.notTrainingByPlan(plan.getId()))
                        .setHandOverIntern(internService.internPass(plan.getId()))
                        .setIsRequest(1);
                if (emailAndNameArr[2].equals("HR")) {
                    emailData.setNamePersonalNeeds(plan.getName())
                            .setLinkProgress(Math.toIntExact(plan.getId()))
                            .setIsRequest(0);
                }
                emailDTO.addNewEmailData(emailData);
            }
        }
        return emailDTO;
    }

    @Override
    public List<String> getEmailAndNameRoleDM(){

        Long[] id = {2L,3L};
        String[] name = {"ROLE_DM","ROLE_TM"};
        String[] displayName = {"Trưởng bộ phận/nhóm", "Quản lí đào tạo"};

        return getUserList(id, name, displayName,"TM");
    }

    public List<String> getEmailAndNameRoleHR(){

        Long[] id = {2L,5L};
        String[] name = {"ROLE_DM","ROLE_HR"};
        String[] displayName = {"Trưởng bộ phận/nhóm","Nhân sự"};

        return getUserList(id, name, displayName, "HR");
    }

    private List<String> getUserList(Long[] id, String[] name, String[] displayName, String userRole) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Role role = new Role();

            role.setId(id[i]);
            role.setName(name[i]);
            role.setDisplay_name(displayName[i]);

            for (User user : userService.findUsersByRoles(role)) {
                users.add(user);
            }
        }

        List<String> emailAndName = new ArrayList<>();

        for (User user : users) {
            emailAndName.add(user.getEmail() + "," + user.getName() + "," + userRole);
        }

        return emailAndName;
    }


}
