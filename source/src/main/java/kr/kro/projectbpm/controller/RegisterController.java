package kr.kro.projectbpm.controller;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.service.EncodeService;
import kr.kro.projectbpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;
    private final EncodeService encodeService;

    @GetMapping("/register")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(User user, RedirectAttributes redirectAttributes) {
        if(userService.existsUser(user.getId())) {
            redirectAttributes.addFlashAttribute("msg", "register_failed");
            return "redirect:/register";
        } else {
            user.setPassword(encodeService.encodePassword(user.getPassword()));
            userService.save(user);
            redirectAttributes.addFlashAttribute("msg", "register_success");
            return "redirect:/";
        }
    }
}
