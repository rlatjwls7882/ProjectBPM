package kr.kro.projectbpm.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.projectbpm.service.EncodeService;
import kr.kro.projectbpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EncodeService encodeService;

    @PostMapping("/idDuplicationCheck")
    public ResponseEntity<?> idDuplicationCheck(String id) {
        return ResponseEntity.ok(!userService.existsById(id));
    }

    @PostMapping("/nameDuplicationCheck")
    public ResponseEntity<?> nameDuplicationCheck(String name) {
        return ResponseEntity.ok(!userService.existsByName(name));
    }

    @PostMapping("/emailDuplicationCheck")
    public ResponseEntity<?> emailDuplicationCheck(String email) {
        return ResponseEntity.ok(!userService.existsByEmail(email));
    }

    @ResponseBody
    @PostMapping("/searchId")
    public HashMap<String, Object> searchId(String email) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("id", userService.getUserByEmail(email).getId());
            map.put("success", Boolean.TRUE);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
        }
        return map;
    }

    @PostMapping("/idCheck")
    public ResponseEntity<?> idCheck(String id) {
        return ResponseEntity.ok(userService.existsById(id));
    }

    @PostMapping("/changePassword")
    public String changePassword(String id, String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            userService.getUserById(id)
                    .changePassword(encodeService.encodePassword(password));
            redirectAttributes.addFlashAttribute("msg", "change_password_success");
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("request.getRequestURI() = " + request.getRequestURI());
            redirectAttributes.addFlashAttribute("msg", "change_password_failed");
            return "redirect:/login/changePassword?searchPasswordForm-id="+id;
        }
    }
}
