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

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ViewService viewService;
    private final BoardService boardService;
    private final CategoryService categoryService;

    @PostMapping("/idDuplicationCheck")
    public ResponseEntity<?> idDuplicationCheck(String id) {
        return ResponseEntity.ok(!userService.existsById(id));
    }

    @PostMapping("/nameDuplicationCheck")
    public ResponseEntity<?> nameDuplicationCheck(String name) {
        return ResponseEntity.ok(!userService.existsByName(name));
    }

    @PostMapping("/emailDuplicationCheck")
    public ResponseEntity<?> emailDuplicationCheck(String email) {
        return ResponseEntity.ok(!userService.existsByEmail(email));
    }

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

    @PostMapping("/idCheck")
    public ResponseEntity<?> idCheck(String id) {
        return ResponseEntity.ok(userService.existsById(id));
    }

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

    @GetMapping("/{id}")
    public String myBlog(@PathVariable String id, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "latest") String sort, Model model) {
        try {
            model.addAttribute("boardList", boardService.getLists(sort, query, id));
            model.addAttribute("query", query);
            model.addAttribute("sort", sort);
            UserDto userDto = userService.getUserById(id);
            userDto.setViewCnt(viewService.getViewCnt(userDto, "today"));
            model.addAttribute("userDto", userDto);
            return "views/user/userPage";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/category/{categoryNum}")
    public String myBlogCategory(@PathVariable String id, @PathVariable long categoryNum, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "latest") String sort, Model model) {
        try {
            model.addAttribute("boardList", boardService.getLists(categoryNum, sort));
            model.addAttribute("query", query);
            model.addAttribute("sort", sort);

            CategoryDto categoryDto = categoryService.getCategory(categoryNum);
            model.addAttribute("categoryDto", categoryDto);

            UserDto userDto = userService.getUserById(id);
            userDto.setViewCnt(viewService.getViewCnt(userDto, "today"));
            model.addAttribute("userDto", userDto);
            return "views/user/userPage";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
