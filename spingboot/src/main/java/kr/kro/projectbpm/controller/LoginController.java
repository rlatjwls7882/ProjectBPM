package kr.kro.projectbpm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.dto.UserDto;
import kr.kro.projectbpm.service.EncodeService;
import kr.kro.projectbpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final EncodeService encodeService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        session.setAttribute("beforeURL", request.getHeader("Referer"));
        return "forward:/login/login";
    }

    @GetMapping("/login/login")
    public String login(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("id")) {
                    model.addAttribute("id", cookie.getValue());
                }
            }
        }
        return "views/login/loginForm";
    }

    @GetMapping("/login/changePassword")
    public String changePassword(@RequestParam(value = "searchPasswordForm-id", defaultValue = "a") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            if(!userService.existsById(id)) throw new Exception();
            model.addAttribute("user", userService.getUserById(id));
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("msg", "not_exist_id");
            return "redirect:/login";
        }
        return "views/login/changePassword";
    }

    @PostMapping("/login")
    public String login(String id, String password, boolean remember, RedirectAttributes redirectAttributes, HttpSession session, HttpServletResponse response) {
        UserDto userDto = userService.getUserById(id);
        if(userDto!=null && encodeService.encodePassword(password).equals(userDto.getPassword())) {
            session.setAttribute("id", userDto.getId());

            Cookie cookie = new Cookie("id", userDto.getId());
            cookie.setMaxAge(60*60*24*30); // 30일
            if(!remember) {
                cookie.setMaxAge(0);
            }
            response.addCookie(cookie);

            redirectAttributes.addFlashAttribute("msg", "login_success");
            if(session.getAttribute("beforeURL") != null) {
                String beforeURL = (String) session.getAttribute("beforeURL");
                session.removeAttribute("beforeURL");
                return "redirect:"+beforeURL;
            } else {
                return "redirect:/";
            }
        } else {
            redirectAttributes.addFlashAttribute("msg", "login_failed");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        request.getSession().invalidate();
        String beforeURL = request.getHeader("Referer");
        if(beforeURL != null) {
            return "redirect:"+beforeURL;
        }
        redirectAttributes.addFlashAttribute("msg", "logout_success");
        return "redirect:/";
    }
}
