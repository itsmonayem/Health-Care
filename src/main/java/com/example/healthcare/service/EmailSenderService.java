package com.example.healthcare.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String toMail, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom("***************@gmail.com");
        mimeMessageHelper.setTo(toMail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);

        this.javaMailSender.send(mimeMessage);
        System.out.println("Main send successfully");
    }
}
