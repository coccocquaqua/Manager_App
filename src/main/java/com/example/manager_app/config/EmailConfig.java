package com.example.manager_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    @Async // sử dụng @Async để gửi email mà không ảnh hưởng đến việc thực thi các công việc khác
    public JavaMailSender getJavaMailSender() { // JavaMailSender là một interface cung cấp các phương thức để gửi email
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); // JavaMailSenderImpl là một lớp cung cấp các phương thức để gửi email
        mailSender.setHost("smtp.gmail.com"); // setHost() thiết lập host của mail server
        mailSender.setPort(587); // setPort() thiết lập port của mail server
        mailSender.setUsername("quynhdtph21491@fpt.edu.vn"); // setUsername() thiết lập tên đăng nhập của mail server
        mailSender.setPassword("tgjb xgae ytoh mnyy"); // setPassword() thiết lập mật khẩu của mail server
        Properties props = mailSender.getJavaMailProperties(); // getJavaMailProperties() trả về các thuộc tính của mail server
        props.put("mail.transport.protocol", "smtp"); // put() thiết lập thuộc tính của mail server
        props.put("mail.smtp.auth", "true"); //
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
