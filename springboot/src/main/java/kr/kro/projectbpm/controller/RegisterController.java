package kr.kro.projectbpm.controller;

import kr.kro.projectbpm.dto.UserDto;
import kr.kro.projectbpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 회원가입 관련 요청을 처리하는 컨트롤러입니다.
 * 회원가입 약관 페이지, 회원가입 폼을 보여주고, 회원가입 처리를 담당합니다.
 * @see UserService
 */
@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    /**
     * 회원가입 약관 페이지로 포워드합니다.
     * @return 회원가입 약관 페이지 경로
     */
    @GetMapping("/register")
    public String register() {
        return "forward:/register/terms";
    }

    /**
     * 회원가입 약관 페이지를 보여줍니다.
     * @return 회원가입 약관 페이지 뷰 이름을 반환합니다.
     */
    @GetMapping("/register/terms")
    public String showTermsPage() {
        return "views/register/terms";
    }

    /**
     * 회원가입 폼을 보여주는 메서드입니다.
     * @return 회원가입 폼 뷰의 이름을 반환합니다.
     */
    @GetMapping("/register/register")
    public String showRegisterForm() {
        return "views/register/registerForm";
    }

    /**
     * 회원가입을 처리하는 메서드입니다.
     * @param userDto 사용자 정보 DTO
     * @param redirectAttributes 리다이렉트 시 플래시 속성에 데이터를 추가하기 위한 객체
     * @return 회원가입 성공 시 홈으로 리다이렉트, 실패 시 다시 회원가입 폼으로 리다이렉트합니다.
     */
    @PostMapping("/register")
    public String register(UserDto userDto, RedirectAttributes redirectAttributes) {
        try {
            if(userService.existsById(userDto.getId())) throw new IllegalStateException("이미 존재하는 사용자 ID입니다.");
            userService.save(userDto);
            redirectAttributes.addFlashAttribute("msg", "register_success");
            return "redirect:/";
        } catch(Exception e) {
            System.out.println("회원가입 오류: " + e);
            redirectAttributes.addFlashAttribute("msg", "register_failed");
            redirectAttributes.addFlashAttribute("id", userDto.getId()); // 제출 폼으로 내용 채워진 채로 돌아가게
            redirectAttributes.addFlashAttribute("name", userDto.getName());
            redirectAttributes.addFlashAttribute("email", userDto.getEmail());
            return "redirect:/register/register";
        }
    }
}
