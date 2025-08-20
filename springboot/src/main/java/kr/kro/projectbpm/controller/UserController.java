package kr.kro.projectbpm.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.projectbpm.dto.CategoryDto;
import kr.kro.projectbpm.dto.UserDto;
import kr.kro.projectbpm.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

/**
 * 사용자 관련 요청을 처리하는 컨트롤러입니다.
 * 이 컨트롤러는 사용자 ID, 이름, 이메일 중복 확인, 아이디 찾기, 비밀번호 변경, 사용자 프로필 페이지 조회 등의 기능을 제공합니다.
 * @see UserService
 * @see ViewService
 * @see BoardService
 * @see CategoryService
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ViewService viewService;
    private final BoardService boardService;
    private final CategoryService categoryService;

    /**
     * 사용자 ID 중복 여부를 확인합니다.
     * @param id 사용자 ID
     * @return 중복 여부
     */
    @PostMapping("/idDuplicationCheck")
    public ResponseEntity<?> idDuplicationCheck(String id) {
        return ResponseEntity.ok(!userService.existsById(id));
    }

    /**
     * 사용자 이름 중복 여부를 확인합니다.
     * @param name 사용자 이름
     * @return 중복 여부
     */
    @PostMapping("/nameDuplicationCheck")
    public ResponseEntity<?> nameDuplicationCheck(String name) {
        return ResponseEntity.ok(!userService.existsByName(name));
    }

    /**
     * 사용자 이메일 중복 여부를 확인합니다.
     * @param email 사용자 이메일
     * @return 중복 여부
     */
    @PostMapping("/emailDuplicationCheck")
    public ResponseEntity<?> emailDuplicationCheck(String email) {
        return ResponseEntity.ok(!userService.existsByEmail(email));
    }

    /**
     * 사용자 아이디를 이메일로 검색합니다.
     * @param email 사용자 이메일
     * @return 아이디 정보가 담긴 맵
     */
    @ResponseBody
    @PostMapping("/searchId")
    public HashMap<String, Object> searchId(String email) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("id", userService.getUserByEmail(email).getId());
            map.put("success", Boolean.TRUE);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
        }
        return map;
    }

    /**
     * 사용자 아이디 중복 여부를 확인합니다.
     * @param id 사용자 아이디
     * @return 중복 여부
     */
    @PostMapping("/idCheck")
    public ResponseEntity<?> idCheck(String id) {
        return ResponseEntity.ok(userService.existsById(id));
    }

    /**
     * 사용자 비밀번호를 변경합니다.
     * @param id 사용자 ID
     * @param password 새 비밀번호
     * @param request HTTP 요청 객체
     * @param redirectAttributes 리다이렉트 속성
     * @return 리다이렉트 URL
     */
    @PostMapping("/changePassword")
    public String changePassword(String id, String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            UserDto userDto = userService.getUserById(id);
            userService.changePassword(userDto, password);
            redirectAttributes.addFlashAttribute("msg", "change_password_success");
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("request.getRequestURI() = " + request.getRequestURI());
            redirectAttributes.addFlashAttribute("msg", "change_password_failed");
            return "redirect:/login/changePassword?searchPasswordForm-id="+id;
        }
    }

    /**
     * 사용자 프로필 페이지를 조회합니다.
     * @param id 사용자 ID
     * @param query 검색어
     * @param sort 정렬 기준
     * @param page 페이지 번호
     * @param model 모델 객체
     * @return 사용자 프로필 페이지 뷰 이름
     */
    @GetMapping("/user/{id}")
    public String myBlog(@PathVariable String id, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "latest") String sort, @RequestParam(defaultValue = "1") int page, Model model) {
        try {
            model.addAttribute("boardList", boardService.getLists(sort, query, id, page-1));
            model.addAttribute("query", query);
            model.addAttribute("sort", sort);
            UserDto userDto = userService.getUserById(id);
            userDto.setViewCnt(viewService.getViewCnt(userDto, "today"));
            model.addAttribute("userDto", userDto);
            model.addAttribute("boardCnt", boardService.getBoardCnt(userDto));
            return "views/user/userPage";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    /**
     * 사용자 프로필 페이지를 카테고리별로 조회합니다.
     * @param id 사용자 ID
     * @param categoryNum 카테고리 번호
     * @param query 검색어
     * @param sort 정렬 기준
     * @param page 페이지 번호
     * @param model 모델 객체
     * @return 사용자 프로필 페이지 뷰 이름
     */
    @GetMapping("/user/{id}/category/{categoryNum}")
    public String myBlogCategory(@PathVariable String id, @PathVariable long categoryNum, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "latest") String sort, @RequestParam(defaultValue = "1") int page, Model model) {
        try {
            model.addAttribute("boardList", boardService.getLists(categoryNum, sort, page-1));
            model.addAttribute("query", query);
            model.addAttribute("sort", sort);

            CategoryDto categoryDto = categoryService.getCategory(categoryNum);
            model.addAttribute("categoryDto", categoryDto);

            UserDto userDto = userService.getUserById(id);
            userDto.setViewCnt(viewService.getViewCnt(userDto, "today"));
            model.addAttribute("userDto", userDto);
            model.addAttribute("boardCnt", boardService.countByCategoryNum(categoryNum));
            return "views/user/userPage";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
