package kr.kro.projectbpm.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.common.util.MaskingUtils;
import kr.kro.projectbpm.service.EmailService;
import kr.kro.projectbpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/sendCode")
    public ResponseEntity<?> sendCode(String type, String email, String id, HttpSession session) {
        try {
            String code = emailService.getCode();
            String content = getContent(type, email, id, code);
            emailService.sendMail(email, content);
            session.setAttribute("code", code);
//            System.out.println("content = " + content);
            return ResponseEntity.ok(Boolean.TRUE);
        } catch (Exception e) {
            System.out.println("이메일 코드 보내기 오류: " + e);
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }

    private String getContent(String type, String email, String id, String code) {
        if(type.equals("register")) {
            return """
                    <div style="background-color: #fefefe; border: 1px solid #888; width: 620px; padding: 12px 20px;">
                      <h3>%s님, 안녕하세요!</h3>
                      <h3>가입해주셔서 감사합니다. 가입을 완료하시려면 인증코드를 입력해주세요.</h3>
                      <h1 style="text-align: center;">%s</h1>
                    </div>
                    """.formatted(id, code);
        } else if(type.equals("searchId")) {
            return """
                    <div style="background-color: #fefefe; border: 1px solid #888; width: 620px; padding: 12px 20px;">
                      <h3>방금 사이트에서 회원님의 아이디 %s에 대한 아이디 찾기 서비스를 이용하셨습니다. 계속하시려면 인증코드를 입력해주세요.</h3>
                      <h1 style="text-align: center;">%s</h1>
                    </div>
                    """.formatted(MaskingUtils.encodeId(userService.getUserByEmail(email).getId()), code);
        } else
//            if(type.equals("searchPassword"))
        {
            return """
                    <div style="background-color: #fefefe; border: 1px solid #888; width: 620px; padding: 12px 20px;">
                      <h3>방금 사이트에서 회원님의 아이디 %s에 대한 비밀번호 찾기 서비스를 이용하셨습니다. 계속하시려면 인증코드를 입력해주세요.</h3>
                      <h1 style="text-align: center;">%s</h1>
                    </div>
                    """.formatted(MaskingUtils.encodeId(id), code);
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
