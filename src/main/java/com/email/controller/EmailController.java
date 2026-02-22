package com.email.controller;

import com.email.dto.EmailRequestDTO;
import com.email.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestBody EmailRequestDTO requestDTO ) throws MessagingException {
        emailService.sendEmail(requestDTO);

        return ResponseEntity.ok("Email sent successfully");
    }
}
