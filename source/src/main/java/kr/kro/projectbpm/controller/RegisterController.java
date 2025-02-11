package kr.kro.projectbpm.controller;

import kr.kro.projectbpm.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(User user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "register_success");
        System.out.println("user = " + user);
        return "redirect:/";
    }
}
