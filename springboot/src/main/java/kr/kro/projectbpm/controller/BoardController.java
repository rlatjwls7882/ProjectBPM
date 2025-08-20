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

/**
 * 게시판 관련 요청을 처리하는 컨트롤러입니다.
 * 게시글 작성, 읽기, 수정, 삭제 등의 기능을 제공합니다.
 * @see BoardService
 * @see UserService
 * @see ViewService
 * @see CategoryService
 */
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final ViewService viewService;
    private final CategoryService categoryService;

    /**
     * 게시판 글 작성 페이지로 이동합니다.
     * @param model 게시글 초기 정보를 담을 모델 객체
     * @param session HTTP 세션 객체
     * @return 게시글 작성 페이지의 뷰 이름
     */
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

    /**
     * 게시글을 작성합니다.
     * @param title 게시글 제목
     * @param content 게시글 내용
     * @param categoryNum 카테고리 번호
     * @param session HTTP 세션 객체
     * @param redirectAttributes 리다이렉트 시 전달할 속성
     * @return 작성 후 게시글 읽기 페이지로 리다이렉트
     */
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

    /**
     * 게시글을 읽어오는 메서드입니다.
     * @param boardNum 게시글 번호
     * @param model 게시글 정보를 담을 모델 객체
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 게시글 읽기 페이지의 뷰 이름
     */
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

    /**
     * 게시글을 검색합니다.
     * @param query 검색어
     * @param sort 정렬 기준 (latest, views, likes)
     * @param page 페이지 번호
     * @param model 현재 쿼리를 저장할 모델 객체
     * @return 홈페이지의 뷰 이름
     */
    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "latest") String sort, @RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("boardList", boardService.getLists(sort, query, page-1));
        model.addAttribute("query", query);
        model.addAttribute("sort", sort);
        return "views/home";
    }

    /**
     * 게시글을 삭제합니다.
     * @param boardNum 게시글 번호
     * @param session HTTP 세션 객체
     * @param redirectAttributes 리다이렉트 시 전달할 속성
     * @return 홈페이지로 리다이렉트
     */
    @PostMapping("/delete")
    public String delete(long boardNum, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String id = SessionUtils.getUserIdOrThrow(session);
            boardService.checkBoard(boardNum, id);
            boardService.deleteBoard(boardNum);
            redirectAttributes.addFlashAttribute("msg", "delete_success");
        } catch(Exception e) {
            System.out.println("Error : delete board(Post) = " + e);
            redirectAttributes.addFlashAttribute("msg", "delete_failed");
        }
        return "redirect:/";
    }

    /**
     * 게시글 수정 페이지로 이동합니다.
     * @param boardNum 게시글 번호
     * @param response HTTP 응답 객체
     * @param session HTTP 세션 객체
     * @param model 게시글 정보를 담을 모델 객체
     * @return 게시글 수정 페이지의 뷰 이름
     */
    @GetMapping("/edit/{boardNum}")
    public String edit(@PathVariable long boardNum, HttpServletResponse response, HttpSession session, Model model) {
        try {
            String id = SessionUtils.getUserIdOrThrow(session);
            boardService.checkBoard(boardNum, id);
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

    /**
     * 게시글을 수정합니다.
     * @param boardNum 게시글 번호
     * @param title 수정할 제목
     * @param content 수정할 내용
     * @param categoryNum 수정할 카테고리 번호
     * @param session HTTP 세션 객체
     * @param redirectAttributes 리다이렉트 시 전달할 속성
     * @return 수정 후 게시글 읽기 페이지로 리다이렉트
     */
    @PostMapping("/edit")
    public String edit(long boardNum, String title, String content, long categoryNum, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String id = SessionUtils.getUserIdOrThrow(session);
            boardService.checkBoard(boardNum, id);
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
