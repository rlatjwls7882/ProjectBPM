package kr.kro.projectbpm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.common.util.SessionUtils;
import kr.kro.projectbpm.dto.BoardDto;
import kr.kro.projectbpm.service.BoardService;
import kr.kro.projectbpm.service.CategoryService;
import kr.kro.projectbpm.service.UserService;
import kr.kro.projectbpm.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final ViewService viewService;
    private final CategoryService categoryService;

    @GetMapping("/write")
    public String write(Model model, HttpSession session) {
        try {
            String id = SessionUtils.getUserIdOrThrow(session);
            BoardDto boardDto = new BoardDto(id, userService.getUserName(id));
            model.addAttribute("boardDto", boardDto);
            model.addAttribute("categoryList", categoryService.getCategories(id));
            return "views/board/write";
        } catch (IllegalStateException e) { // 세션에 id가 없을 때
            session.setAttribute("beforeURL", "/write");
            return "redirect:/login/login";
        } catch (Exception e) {
            System.out.println("Error : write board(Get) = " + e);
            return "redirect:/login/login";
        }
    }

    @PostMapping("/write")
    public String write(String title, String content, long categoryNum, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String id = SessionUtils.getUserIdOrThrow(session);
            long boardNum = boardService.createBoard(title, content, id, categoryNum);
            redirectAttributes.addFlashAttribute("msg", "write_success");
            return "redirect:/read/"+boardNum;
        } catch (Exception e) {
            System.out.println("Error : write board(Post) = " + e);
            redirectAttributes.addFlashAttribute("msg", "write_failed");
            redirectAttributes.addFlashAttribute("title", title);
            redirectAttributes.addFlashAttribute("content", content);
            return "redirect:/write";
        }
    }

    @GetMapping("/read/{boardNum}")
    public String read(@PathVariable long boardNum, Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean chk = true;
            String cookieName = "visited_"+boardNum;
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for(Cookie cookie : cookies) {
                    if(cookie.getName().equals(cookieName)) {
                        chk = false;
                        break;
                    }
                }
            }
            BoardDto boardDto = boardService.getBoard(boardNum);
            if(chk) {
                Cookie cookie = new Cookie(cookieName, "true");
                cookie.setMaxAge(60*60*24); // 24시간
                cookie.setPath("/read");
                response.addCookie(cookie);
                viewService.createView(boardDto);
            }
            model.addAttribute("boardDto", boardDto);
            return "views/board/read";
        } catch (Exception e) {
            System.out.println("Error : read board(Get) = " + e);
            response.setStatus(HttpServletResponse.SC_OK);
            return "error/404";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "latest") String sort, @RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("boardList", boardService.getLists(sort, query, page-1));
        model.addAttribute("query", query);
        model.addAttribute("sort", sort);
        return "views/home";
    }

    @PostMapping("/delete")
    public String delete(long boardNum, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String id = SessionUtils.getUserIdOrThrow(session);
            if(boardService.checkBoard(boardNum, id)) throw new Exception("id 불일치");
            boardService.deleteBoard(boardNum);
            redirectAttributes.addFlashAttribute("msg", "delete_success");
        } catch(Exception e) {
            System.out.println("Error : delete board(Post) = " + e);
            redirectAttributes.addFlashAttribute("msg", "delete_failed");
        }
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(long boardNum, HttpServletResponse response, HttpSession session, Model model) {
        try {
            String id = SessionUtils.getUserIdOrThrow(session);
            if(boardService.checkBoard(boardNum, id)) throw new Exception("id 불일치");
            BoardDto boardDto = boardService.getBoard(boardNum);
            boardDto.setEdit();
            model.addAttribute("boardDto", boardDto);
            model.addAttribute("categoryList", categoryService.getCategories(boardDto.getUserId()));
            return "views/board/write";
        } catch(Exception e) {
            System.out.println("Error : edit board(Get) = " + e);
            response.setStatus(HttpServletResponse.SC_OK);
            return "error/404";
        }
    }

    @PostMapping("/edit")
    public String edit(long boardNum, String title, String content, long categoryNum, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String id = SessionUtils.getUserIdOrThrow(session);
            if(boardService.checkBoard(boardNum, id)) throw new Exception("id 불일치");
            boardService.editBoard(boardNum, title, content);
            categoryService.linkCategory(boardNum, categoryNum);
            redirectAttributes.addFlashAttribute("msg", "edit_success");
        } catch (Exception e) {
            System.out.println("Error : edit board(Post) = " + e);
            redirectAttributes.addFlashAttribute("msg", "edit_failed");
        }
        return "redirect:/read/"+boardNum;
    }
}
