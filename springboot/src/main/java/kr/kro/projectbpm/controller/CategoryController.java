package kr.kro.projectbpm.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.projectbpm.dto.CategoryDto;
import kr.kro.projectbpm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(String userId) {
        try {
            return new ResponseEntity<>(categoryService.getCategories(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto, HttpSession session) {
        try {
            categoryService.changeCategory(categoryDto, (String) session.getAttribute("id"));
        } catch (Exception e) {
            return ResponseEntity.ok(Boolean.FALSE);
        }
        return ResponseEntity.ok(Boolean.TRUE);
    }

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
