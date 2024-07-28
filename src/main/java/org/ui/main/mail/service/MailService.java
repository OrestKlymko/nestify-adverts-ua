package org.ui.main.mail.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.ui.main.mail.dto.MailStructure;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String email, MailStructure mailStructure) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("notify@nestify.sk");
        mailMessage.setSubject(mailStructure.subject());
        mailMessage.setText(mailStructure.message());
        mailMessage.setTo(email);
        javaMailSender.send(mailMessage);
    }
}
