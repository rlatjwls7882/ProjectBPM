package kr.kro.projectbpm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.dto.UserDto;
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

    @GetMapping("/login")
    public String loginRedirect(HttpServletRequest request, HttpSession session) {
        session.setAttribute("beforeURL", request.getHeader("Referer"));
        return "forward:/login/login";
    }

    @GetMapping("/login/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
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
    public String changePassword(@RequestParam(value = "searchPasswordForm-id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("user", userService.getUserById(id));
        } catch(Exception e) {
            System.out.println("비밀번호 변경 오류: " + e);
            redirectAttributes.addFlashAttribute("msg", "not_exist_id");
            return "redirect:/login";
        }
        return "views/login/changePassword";
    }

    @PostMapping("/login")
    public String login(String id, String password, boolean remember, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(true);
            UserDto userDto = userService.getUserById(id);
            userService.checkPassword(userDto, password);
            session.setAttribute("id", userDto.getId());

            Cookie cookie;
            if(remember) {
                cookie = new Cookie("id", userDto.getId());
                cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
            } else {
                cookie = new Cookie("id", null);
                cookie.setMaxAge(0); // 즉시 삭제
            }
            cookie.setPath("/");
            response.addCookie(cookie);

            redirectAttributes.addFlashAttribute("msg", "login_success");
            if(session.getAttribute("beforeURL") != null) { // 이전 URL이 있다면 되돌아가기
                String beforeURL = (String) session.getAttribute("beforeURL");
                session.removeAttribute("beforeURL");
                return "redirect:"+beforeURL;
            } else {
                return "redirect:/";
            }
        } catch(Exception e) {
            System.out.println("로그인 오류: " + e);
            redirectAttributes.addFlashAttribute("msg", "login_failed");
            redirectAttributes.addFlashAttribute("id", id);
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        request.getSession().invalidate();
        String beforeURL = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("msg", "logout_success");
        if(beforeURL != null) { // 이전 URL이 있다면 되돌아가기
            return "redirect:"+beforeURL;
        }
        return "redirect:/";
    }
}
