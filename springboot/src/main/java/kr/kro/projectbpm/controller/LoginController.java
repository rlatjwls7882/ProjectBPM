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

/**
 * LoginController는 로그인 관련 요청을 처리하는 컨트롤러입니다.
 * 이 컨트롤러는 로그인 페이지로의 리다이렉트, 로그인 폼 표시, 비밀번호 변경, 로그인 처리, 로그아웃 등을 담당합니다.
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    /**
     * 로그인 페이지로 리다이렉트합니다.
     * 이전 URL을 세션에 저장하여 로그인 후 되돌아갈 수 있도록 합니다.
     *
     * @param request HTTP 요청 객체
     * @param session HTTP 세션 객체
     * @return 로그인 페이지로 포워드합니다.
     */
    @GetMapping("/login")
    public String loginRedirect(HttpServletRequest request, HttpSession session) {
        session.setAttribute("beforeURL", request.getHeader("Referer"));
        return "forward:/login/login";
    }

    /**
     * 로그인 폼을 보여주는 메서드입니다.
     * 쿠키에서 ID를 가져와서 모델에 추가합니다.
     *
     * @param request HTTP 요청 객체
     * @param model   뷰에 데이터를 전달하기 위한 모델 객체
     * @return 로그인 폼 뷰의 이름을 반환합니다.
     */
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

    /**
     * 비밀번호 변경 페이지로 이동하는 메서드입니다.
     * 사용자 ID를 기반으로 사용자 정보를 가져와서 모델에 추가합니다.
     * 사용자 ID가 존재하지 않으면 에러 메시지를 리다이렉트 속성에 추가하고 로그인 페이지로 리다이렉트합니다.
     *
     * @param id 사용자 ID
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @param redirectAttributes 리다이렉트 시 메시지를 전달하기 위한 객체
     * @return 비밀번호 변경 페이지의 뷰 이름을 반환합니다.
     */
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

    /**
     * 로그인 처리 메서드입니다.
     * 사용자가 입력한 ID와 비밀번호를 검증하고, 세션에 사용자 ID를 저장합니다.
     * 쿠키에 ID를 저장하여 기억하기 기능을 구현합니다.
     * 로그인 성공 시 이전 URL로 리다이렉트하거나 홈 페이지로 이동합니다.
     *
     * @param id 사용자 ID
     * @param password 사용자 비밀번호
     * @param remember 기억하기 버튼 체크 여부
     * @param redirectAttributes 리다이렉트 시 메시지를 전달하기 위한 객체
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 로그인 성공 시 리다이렉트할 URL을 반환합니다.
     */
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

    /**
     * 로그아웃 처리 메서드입니다.
     * 세션을 무효화하고, 이전 URL로 리다이렉트합니다.
     * 이전 URL이 없으면 홈 페이지로 이동합니다.
     *
     * @param request HTTP 요청 객체
     * @param redirectAttributes 리다이렉트 시 메시지를 전달하기 위한 객체
     * @return 로그아웃 후 리다이렉트할 URL을 반환합니다.
     */
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
