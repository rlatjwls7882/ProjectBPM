package kr.kro.projectbpm.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public String getCode() {
        String code = "";
        final String chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i=0;i<5;i++) {
            code += chars.charAt((int)(Math.random()*chars.length()));
        }
        return code;
    }

    @Override
    public MimeMessage createMail(String toMail, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(fromMail);
            message.setRecipients(MimeMessage.RecipientType.TO, toMail);
            message.setSubject("이메일 인증");
            message.setText(content,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void sendMail(String toMail, String content) {
        MimeMessage message = createMail(toMail, content);
        javaMailSender.send(message);
    }
}