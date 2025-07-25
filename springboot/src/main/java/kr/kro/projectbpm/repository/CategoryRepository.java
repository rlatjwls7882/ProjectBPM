package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    long countByUserId(String userId);
    @Query("""
            SELECT c FROM Category c
            WHERE c.user.id = :userId
            ORDER BY c.order ASC
            """)
    List<Category> findCategoriesByUserId(String userId);
    Category findCategoryByNum(Long num);
    void deleteByNum(Long num);
}
