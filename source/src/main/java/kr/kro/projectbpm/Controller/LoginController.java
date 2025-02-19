package kr.kro.projectbpm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.service.EncodeService;
import kr.kro.projectbpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final EncodeService encodeService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("id")) {
                model.addAttribute("id", cookie.getValue());
            }
        }
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(String id, String password, boolean remember, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUser(id);
        System.out.println("user = " + user);
        if(user!=null && encodeService.encodePassword(password).equals(user.getPassword())) {
            /* 로그인 정보 저장 */
            HttpSession session = request.getSession();
            session.setAttribute("id", user.getId());
            /* 로그인 정보 저장 */

            /* 아이디 저장 */
            Cookie cookie = new Cookie("id", user.getId());
            if(!remember) {
                cookie.setMaxAge(0);
            }
            response.addCookie(cookie);
            /* 아이디 저장 */

            redirectAttributes.addFlashAttribute("msg", "login_success");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("msg", "login_failed");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        request.getSession().invalidate();
        redirectAttributes.addFlashAttribute("msg", "logout_success");
        return "redirect:/";
    }
}
