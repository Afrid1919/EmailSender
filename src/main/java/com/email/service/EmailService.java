package com.email.service;

import com.email.dto.EmailRequestDTO;
import com.email.entity.Email_Template;
import com.email.repository.EmailRepository;
import com.email.utility.TemplateProcessor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final TemplateProcessor templateProcessor;

    public EmailService(JavaMailSender mailSender,
                        EmailRepository emailRepository,
                        TemplateProcessor templateProcessor) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
        this.templateProcessor = templateProcessor;
    }

    public void sendEmail(EmailRequestDTO request) throws MessagingException {

        Email_Template template = emailRepository
                .findByTemplateCodeAndActiveTrue(request.getTemplateCode())
                .orElseThrow(() ->
                        new RuntimeException("Template not found"));

        //it process the subject
        String processedSubject = templateProcessor
                .processTemplate(template.getSubject(), request.getVariables());
        //it process the body
        String processedBody = templateProcessor
                .processTemplate(template.getBody(), request.getVariables());


        //simple mail
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(request.getTo());
//        message.setSubject(template.getSubject());
//        message.setText(processedBody);
//        mailSender.send(message);


        //HTML template mail, used for multimedia attachment

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(request.getTo());
        helper.setSubject(processedSubject);
        helper.setText(processedBody, true); // true enables HTML
        mailSender.send(message);

    }

}
