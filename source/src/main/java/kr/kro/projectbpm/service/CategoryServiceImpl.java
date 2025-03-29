package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.Board;
import kr.kro.projectbpm.domain.Category;
import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.dto.CategoryDto;
import kr.kro.projectbpm.repository.BoardRepository;
import kr.kro.projectbpm.repository.CategoryRepository;
import kr.kro.projectbpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public CategoryDto addCategory(String name, String userId) {
        User user = userRepository.findUserById(userId);
        CategoryDto categoryDto = new CategoryDto(name, userId, getCategoryCount(userId)+1);
        Category category = new Category(categoryDto, user);
        categoryRepository.save(category);
        return new CategoryDto(category);
    }

    @Override
    public long getCategoryCount(String userId) {
        return categoryRepository.countByUserId(userId);
    }

    @Override
    public List<CategoryDto> getCategories(String userId) {
        return categoryRepository.findCategoriesByUserId(userId).stream().map(CategoryDto::new).toList();
    }

    @Override
    public void changeCategory(CategoryDto categoryDto, String userId) throws Exception {
        Category category = categoryRepository.findCategoryByNum(categoryDto.getNum());
        if(!category.getUser().getId().equals(userId)) throw new Exception("id 불일치");
        category.changeCategory(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public boolean deleteCategory(long num, String userId) {
        try {
            Category category = categoryRepository.findCategoryByNum(num);
            if(!category.getUser().getId().equals(userId)) return false;

            categoryRepository.deleteByNum(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void linkCategory(long boardNum, long categoryNum) {
        Board board = boardRepository.findByBoardNum(boardNum);

        if(categoryNum==0) {
            board.setCategory(null);
        } else {
            Category category = categoryRepository.findCategoryByNum(categoryNum);
            board.setCategory(category);
        }
        boardRepository.save(board);
    }

    @Override
    public CategoryDto getCategory(long num) {
        return new CategoryDto(categoryRepository.findCategoryByNum(num));
    }
}
