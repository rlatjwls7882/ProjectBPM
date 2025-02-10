package kr.kro.projectbpm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(String id, String password, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "login_success");
        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("password", password);
        return "redirect:/";
    }
}
