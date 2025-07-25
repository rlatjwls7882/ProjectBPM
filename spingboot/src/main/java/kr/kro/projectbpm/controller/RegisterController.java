package kr.kro.projectbpm.controller;

import kr.kro.projectbpm.dto.UserDto;
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
    public String register(UserDto userDto, RedirectAttributes redirectAttributes) {
        if(userService.existsById(userDto.getId())) {
            redirectAttributes.addFlashAttribute("msg", "register_failed");
            redirectAttributes.addFlashAttribute("id", userDto.getId()); // 제출 폼으로 내용 채워진 채로 돌아가게
            redirectAttributes.addFlashAttribute("name", userDto.getName());
            redirectAttributes.addFlashAttribute("email", userDto.getEmail());
            return "redirect:/register/register";
        } else {
            userService.save(userDto);
            redirectAttributes.addFlashAttribute("msg", "register_success");
            return "redirect:/";
        }
    }
}
