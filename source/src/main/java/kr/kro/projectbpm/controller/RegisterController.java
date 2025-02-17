package kr.kro.projectbpm.controller;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.repository.UserRepository;
import kr.kro.projectbpm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static kr.kro.projectbpm.EncodePassword.encodePassword;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(String id, String password, String name, String email, RedirectAttributes redirectAttributes) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodePassword(password));

        System.out.println("user = " + user);
        if(userService.existsUser(id)) {
            redirectAttributes.addFlashAttribute("msg", "register_failed");
            return "redirect:/resister";
        } else {
            userService.save(user);
            redirectAttributes.addFlashAttribute("msg", "register_success");
            return "redirect:/";
        }
    }
}
