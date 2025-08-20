package kr.kro.projectbpm.service;

import kr.kro.projectbpm.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(String name, String userId);
    long getCategoryCount(String userId);
    List<CategoryDto> getCategories(String userId);
    void changeCategory(CategoryDto categoryDto, String userId);
    boolean deleteCategory(long num, String userId);
    void linkCategory(long boardNum, long categoryNum);
    CategoryDto getCategory(long num);
}
