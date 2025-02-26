package kr.kro.projectbpm.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.service.EmailService;
import kr.kro.projectbpm.service.EncodeService;
import kr.kro.projectbpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final EncodeService encodeService;
    private final UserService userService;

    @PostMapping("/sendCodeForRegister")
    public ResponseEntity<?> sendCodeForRegister(String email, String id, HttpSession session) {
        try {
            String code = emailService.getCode();
            String content = """
                            <div class="container" style="background-color: #fefefe; border: 1px solid #888; width: 620px; padding: 12px 20px;">
                              <h3>%s님, 안녕하세요!</h3>
                              <h3>가입해주셔서 감사합니다. 가입을 완료하시려면 인증코드를 입력해주세요.</h3>
                              <h1 style="text-align: center;">%s</h1>
                            </div>
                            """.formatted(id, code);
            emailService.sendMail(email, content);
            session.setAttribute("code", code);
            return ResponseEntity.ok(Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }

    @PostMapping("/sendCodeForSearchId")
    public ResponseEntity<?> sendCodeForSearchId(String email, HttpSession session) {
        try {
            String code = emailService.getCode();
            String content = """
                            <div class="container" style="background-color: #fefefe; border: 1px solid #888; width: 620px; padding: 12px 20px;">
                              <h3>방금 사이트에서 회원님의 아이디 %s에 대한 아이디 찾기 서비스를 이용하셨습니다. 계속하시려면 인증코드를 입력해주세요.</h3>
                              <h1 style="text-align: center;">%s</h1>
                            </div>
                            """.formatted(encodeService.encodeId(userService.getUserByEmail(email).getId()), code);
            emailService.sendMail(email, content);
            session.setAttribute("code", code);
            return ResponseEntity.ok(Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }

    @PostMapping("/sendCodeForSearchPassword")
    public ResponseEntity<?> sendCodeForSearchPassword(String email, String id, HttpSession session) {
        try {
            String code = emailService.getCode();
            String content = """
                            <div class="container" style="background-color: #fefefe; border: 1px solid #888; width: 620px; padding: 12px 20px;">
                              <h3>방금 사이트에서 회원님의 아이디 %s에 대한 비밀번호 찾기 서비스를 이용하셨습니다. 계속하시려면 인증코드를 입력해주세요.</h3>
                              <h1 style="text-align: center;">%s</h1>
                            </div>
                            """.formatted(encodeService.encodeId(id), code);
            emailService.sendMail(email, content);
            session.setAttribute("code", code);
            return ResponseEntity.ok(Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }

    @PostMapping("/checkCode")
    public ResponseEntity<?> checkCode(String userCode, HttpSession session) {
        try {
            boolean chk = userCode.equals(session.getAttribute("code"));
            session.removeAttribute("code");
            return ResponseEntity.ok(chk);
        } catch(Exception e) {
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }
}
