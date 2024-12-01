package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class EmailSender {

    private final MailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom(this.sender);
        message.setSubject(subject);
        message.setText(msg);

        mailSender.send(message);
    }

}
