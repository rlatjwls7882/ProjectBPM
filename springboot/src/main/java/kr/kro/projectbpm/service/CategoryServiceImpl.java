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

/**
 * 카테고리 서비스 구현 클래스입니다.
 * 카테고리 추가, 조회, 변경, 삭제 등의 기능을 제공합니다.
 * @see CategoryRepository
 * @see UserRepository
 * @see BoardRepository
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * 카테고리를 추가합니다.
     * @param name 카테고리 이름
     * @param userId 사용자 ID
     * @return 추가된 카테고리 정보
     */
    @Override
    public CategoryDto addCategory(String name, String userId) {
        User user = userRepository.findUserById(userId);
        CategoryDto categoryDto = new CategoryDto(name, userId, getCategoryCount(userId)+1);
        Category category = new Category(categoryDto, user);
        categoryRepository.save(category);
        return new CategoryDto(category);
    }

    /**
     * 사용자 ID에 해당하는 카테고리 개수를 반환합니다.
     * @param userId 사용자 ID
     * @return 카테고리 개수
     */
    @Override
    public long getCategoryCount(String userId) {
        return categoryRepository.countByUserId(userId);
    }

    /**
     * 사용자 ID에 해당하는 카테고리 목록을 반환합니다.
     * @param userId 사용자 ID
     * @return 카테고리 목록
     */
    @Override
    public List<CategoryDto> getCategories(String userId) {
        return categoryRepository.findCategoriesByUserId(userId).stream().map(CategoryDto::new).toList();
    }

    /**
     * 카테고리 정보를 변경합니다.
     * @param categoryDto 변경할 카테고리 정보
     * @param userId 사용자 ID
     * @throws IllegalStateException 해당 카테고리의 사용자 ID와 요청한 사용자 ID가 일치하지 않을 경우 예외 발생
     */
    @Override
    public void changeCategory(CategoryDto categoryDto, String userId) {
        Category category = categoryRepository.findCategoryByNum(categoryDto.getNum());
        if(!category.getUser().getId().equals(userId)) throw new IllegalStateException("id 불일치");
        category.changeCategory(categoryDto);
        categoryRepository.save(category);
    }

    /**
     * 카테고리를 삭제합니다.
     * @param num 카테고리 번호
     * @param userId 사용자 ID
     * @return 삭제 성공 여부
     */
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

    /**
     * 게시판에 카테고리를 연결합니다.
     * @param boardNum 게시판 번호
     * @param categoryNum 카테고리 번호 (0이면 연결 해제)
     */
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

    /**
     * 카테고리 번호에 해당하는 카테고리 정보를 반환합니다.
     * @param num 카테고리 번호
     * @return 카테고리 정보
     */
    @Override
    public CategoryDto getCategory(long num) {
        return new CategoryDto(categoryRepository.findCategoryByNum(num));
    }
}
