package kr.kro.projectbpm.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.service.MailService;
import kr.kro.projectbpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService emailService;
    private final UserService userService; // 아이디와 이메일 하나만 사용하게 변경 예정

    @PostMapping("/sendCode")
    public ResponseEntity<?> sendCode(String toMail, String id, HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            session.setAttribute("code", emailService.sendMail(toMail, id));
            return ResponseEntity.ok(Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }

    @GetMapping("/checkCode")
    public ResponseEntity<?> checkCode(String userCode, HttpSession session) {
        return ResponseEntity.ok(userCode.equals(session.getAttribute("code")));
    }
}
