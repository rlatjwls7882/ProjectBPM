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

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("boardList", boardService.getLists());
        model.addAttribute("sort", "latest");
        return "views/home";
    }
}
