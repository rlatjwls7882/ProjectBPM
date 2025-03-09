package kr.kro.projectbpm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.domain.Board;
import kr.kro.projectbpm.service.BoardService;
import kr.kro.projectbpm.service.UserService;
import kr.kro.projectbpm.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final ViewService viewService;

    @GetMapping("/write")
    public String write(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("id") == null) {
            session.setAttribute("beforeURL", "/write");
            return "redirect:/login/login";
        }
        return "views/board/write";
    }

    @PostMapping("/write")
    public String write(String title, String content, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            boardService.createBoard(title, content, userService.getUserById((String) session.getAttribute("id")));
            redirectAttributes.addFlashAttribute("msg", "write_success");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "write_failed");
            redirectAttributes.addFlashAttribute("title", title);
            redirectAttributes.addFlashAttribute("content", content);
            return "redirect:/write";
        }
    }

    @GetMapping("/read")
    public String read(long boardNum, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean chk = true;
            String cookieName = "visited_"+boardNum;
            for(Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals(cookieName)) {
                    chk = false;
                }
            }
            Board board = boardService.getBoard(boardNum);
            if(chk) {
                Cookie cookie = new Cookie(cookieName, "true");
                cookie.setMaxAge(60*30); // 30분
                cookie.setPath("/read");
                response.addCookie(cookie);
                viewService.createView(board);
            }
            model.addAttribute("board", board);
            return "views/board/read";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "read_failed");
            return "redirect:/";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "latest") String sort, HttpSession session, Model model) {
        model.addAttribute("list", boardService.getLists(sort, query));
        model.addAttribute("query", query);
        model.addAttribute("sort", sort);
        return "views/home";
    }

    @PostMapping("/delete")
    public String delete(long boardNum, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            if(boardService.checkBoard(boardNum, session.getAttribute("id"))) throw new Exception("id 불일치");
            boardService.deleteBoard(boardNum);
            redirectAttributes.addFlashAttribute("msg", "delete_success");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("msg", "delete_failed");
        }
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(long boardNum, HttpSession session, Model model) {
        try {
            if(boardService.checkBoard(boardNum, session.getAttribute("id"))) throw new Exception("id 불일치");
            model.addAttribute("board", boardService.getBoard(boardNum));
            return "views/board/edit";
        } catch(Exception e) {
            return "redirect:/";
        }
    }

    @PostMapping("/edit")
    public String edit(long boardNum, String title, String content, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            if(boardService.checkBoard(boardNum, session.getAttribute("id"))) throw new Exception("id 불일치");
            boardService.editBoard(boardNum, title, content);
            redirectAttributes.addFlashAttribute("msg", "edit_success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "edit_failed");
        }
        return "redirect:/read?boardNum="+boardNum;
    }
}
