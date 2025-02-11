package kr.kro.projectbpm.controller;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(String id, String password, RedirectAttributes redirectAttributes) {
        System.out.println("id = " + id);
        System.out.println("password = " + password);
        User user = userRepository.findById(id).orElse(null);
        System.out.println("user = " + user);
        if(user!=null) {
            redirectAttributes.addFlashAttribute("msg", "login_success");
            redirectAttributes.addFlashAttribute("user", user);
        } else {
            redirectAttributes.addFlashAttribute("msg", "login_failed");
        }

        return "redirect:/";
    }

}
