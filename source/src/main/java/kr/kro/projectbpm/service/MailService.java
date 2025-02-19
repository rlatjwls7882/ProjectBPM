
package kr.kro.projectbpm.service;

import jakarta.mail.internet.MimeMessage;

public interface MailService {
    MimeMessage createMail(String toMail, String id, String code);

    String sendMail(String toMail, String id);
}

