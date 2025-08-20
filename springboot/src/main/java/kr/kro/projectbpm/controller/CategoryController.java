package kr.kro.projectbpm.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.dto.CategoryDto;
import kr.kro.projectbpm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryController는 카테고리 관련 요청을 처리하는 컨트롤러입니다.
 * 이 컨트롤러는 카테고리 목록 조회, 생성, 업데이트, 삭제 기능을 제공합니다.
 * @see CategoryController
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 카테고리 목록을 가져옵니다.
     * @param userId 사용자 ID
     * @return 카테고리 목록
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(String userId) {
        try {
            return new ResponseEntity<>(categoryService.getCategories(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 카테고리를 업데이트합니다.
     * @param categoryDto 카테고리 정보
     * @param session HTTP 세션
     * @return 업데이트 성공 여부
     */
    @PatchMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto, HttpSession session) {
        try {
            categoryService.changeCategory(categoryDto, (String) session.getAttribute("id"));
        } catch (Exception e) {
            return ResponseEntity.ok(Boolean.FALSE);
        }
        return ResponseEntity.ok(Boolean.TRUE);
    }

    /**
     * 카테고리를 생성합니다.
     * @param name 카테고리 이름
     * @param session HTTP 세션
     * @return 생성된 카테고리 정보
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(String name, HttpSession session) {
        try {
            String userId = (String) session.getAttribute("id");
            CategoryDto categoryDto = categoryService.addCategory(name, userId);
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 카테고리를 삭제합니다.
     * @param num 카테고리 번호
     * @param session HTTP 세션
     * @return 삭제 성공 여부
     */
    @DeleteMapping
    public ResponseEntity<?> deleteCategory(long num, HttpSession session) {
        try {
            if(!categoryService.deleteCategory(num, (String) session.getAttribute("id"))) throw new Exception("id 불일치 혹은 삭제 실패");
        } catch (Exception e) {
            return ResponseEntity.ok(Boolean.FALSE);
        }
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
