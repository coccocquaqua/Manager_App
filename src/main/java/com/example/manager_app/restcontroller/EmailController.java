package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.EmailRequest;
import com.example.manager_app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendSimpleMessage(emailRequest);
        return "Email sent successfully";
    }

}
