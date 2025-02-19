package kr.kro.projectbpm.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    static String getCode(int len) {
        String code = "";
        final String chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < len; i++) {
            code += chars.charAt((int) (Math.random() * chars.length()));
        }
        return code;
    }

    @Override
    public MimeMessage createMail(String toMail, String id, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(fromMail);
            message.setRecipients(MimeMessage.RecipientType.TO, toMail);
            message.setSubject("이메일 인증");
            String body = """
                            <div class="container" style="background-color: #fefefe; border: 1px solid #888; width: 620px; padding: 12px 20px;">
                              <h3>%s님, 안녕하세요!</h3>
                              <h3>가입해주셔서 감사합니다. 가입을 완료하시려면 인증코드를 입력해주세요.</h3>
                              <h1 style="text-align: center;">%s</h1>
                            </div>
                            """.formatted(id, code);
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public String sendMail(String toMail, String id) {
        String code = getCode(5);
        MimeMessage message = createMail(toMail, id, code);
        javaMailSender.send(message);
        return code;
    }
}