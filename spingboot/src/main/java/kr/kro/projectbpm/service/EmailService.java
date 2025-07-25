
package kr.kro.projectbpm.service;

import jakarta.mail.internet.MimeMessage;

public interface EmailService {
    String getCode();
    MimeMessage createMail(String toMail, String content);
    void sendMail(String toMail, String content);
}

