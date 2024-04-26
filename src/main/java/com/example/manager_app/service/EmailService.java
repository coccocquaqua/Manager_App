package com.example.manager_app.service;

import com.example.manager_app.dto.EmailRequest;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService{
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    @Autowired
    UserService userService;

    @Autowired
    public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(EmailRequest request) {
        String[] to= request.getTo();
        for (String item : to) {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom("quynhdtph21491@fpt.edu.vn");
                helper.setTo(item);
                helper.setSubject("Thông báo");
                String html = templateEngine.process("email-template.html", new Context());
                helper.setText(html, true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}