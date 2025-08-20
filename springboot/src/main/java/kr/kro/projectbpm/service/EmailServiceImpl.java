package kr.kro.projectbpm.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 이메일 서비스 구현 클래스입니다.
 * 이메일 인증 코드 생성, 이메일 메시지 생성, 이메일 전송 기능을 제공합니다.
 * @see JavaMailSender
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    /**
     * 이메일 인증 코드를 생성합니다.
     * @return 5자리의 랜덤한 문자열
     */
    @Override
    public String getCode() {
        String code = "";
        final String chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i=0;i<5;i++) {
            code += chars.charAt((int)(Math.random()*chars.length()));
        }
        return code;
    }

    /**
     * 이메일 메시지를 생성합니다.
     * @param toMail 받는 사람 이메일
     * @param content 이메일 내용
     * @return MimeMessage 객체
     */
    @Override
    public MimeMessage createMail(String toMail, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(fromMail);
            message.setRecipients(MimeMessage.RecipientType.TO, toMail);
            message.setSubject("이메일 인증");
            message.setText(content,"UTF-8", "html");
        } catch (MessagingException e) {
            System.out.println("이메일 메세지 생성중 에러 = " + e);
        }
        return message;
    }

    /**
     * 이메일을 전송합니다.
     * @param toMail 받는 사람 이메일
     * @param content 이메일 내용
     */
    @Override
    public void sendMail(String toMail, String content) {
        MimeMessage message = createMail(toMail, content);
        javaMailSender.send(message);
    }
}