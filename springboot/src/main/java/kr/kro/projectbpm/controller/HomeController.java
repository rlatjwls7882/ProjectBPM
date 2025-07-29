package kr.kro.projectbpm.controller;

import kr.kro.projectbpm.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;

    /**
     * 홈 페이지를 반환합니다.
     * 이 메서드는 게시판 목록을 가져와서 모델에 추가하고, 최신 게시글을 기준으로 정렬합니다.
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체입니다.
     * @return 홈 페이지의 뷰 이름을 반환합니다.
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("boardList", boardService.getLists());
        model.addAttribute("sort", "latest");
        return "views/home";
    }
}
