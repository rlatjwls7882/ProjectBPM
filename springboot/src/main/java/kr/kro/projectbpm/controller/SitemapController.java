package kr.kro.projectbpm.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.projectbpm.dto.BoardDto;
import kr.kro.projectbpm.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
public class SitemapController {
    private final BoardService boardService;

    @GetMapping("/sitemap.xml")
    public String getSitemap(HttpServletRequest request) {
        List<BoardDto> boards = boardService.getBoards();

        String baseUrl = request.getScheme() + "://" + request.getServerName();

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        // 루트 경로 (게시글 목록 페이지)
        sb.append("<url><loc>").append(baseUrl).append("/</loc>")
                .append("<changefreq>daily</changefreq>")
                .append("<priority>1.0</priority></url>\n");

//        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        // 개별 게시글
        for (BoardDto board : boards) {
//            String formattedDate = isoFormatter.format(board.getUpDate());
            ZonedDateTime zdt = board.getUpDate().toInstant().atZone(ZoneId.systemDefault());
            String formattedDate = isoFormatter.format(zdt);
            sb.append("<url>")
                    .append("<loc>").append(baseUrl).append("/read/").append(board.getBoardNum()).append("</loc>")
                    .append("<lastmod>").append(formattedDate).append("</lastmod>")
                    .append("<changefreq>weekly</changefreq>")
                    .append("<priority>0.6</priority>")
                    .append("</url>\n");
        }

        sb.append("</urlset>");
        return sb.toString();
    }
}
