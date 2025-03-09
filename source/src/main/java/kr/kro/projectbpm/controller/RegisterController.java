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
        return "forward:/register/terms";
    }

    @GetMapping("/register/terms")
    public String register1() {
        return "views/register/terms";
    }

    @GetMapping("/register/register")
    public String register2() {
        return "views/register/registerForm";
    }

    @PostMapping("/register")
    public String register(User user, RedirectAttributes redirectAttributes) {
        if(userService.existsById(user.getId())) {
            redirectAttributes.addFlashAttribute("msg", "register_failed");
            redirectAttributes.addFlashAttribute("id", user.getId());
            redirectAttributes.addFlashAttribute("name", user.getName());
            redirectAttributes.addFlashAttribute("email", user.getEmail());
            return "redirect:/register/register";
        } else {
            user.changePassword(encodeService.encodePassword(user.getPassword()));
            System.out.println("user = " + user);
            userService.save(user);
            redirectAttributes.addFlashAttribute("msg", "register_success");
            return "redirect:/";
        }
    }
}
