package kr.kro.projectbpm.dto;

import kr.kro.projectbpm.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDto {
    private Long num;
    private String name;
    private Long order;
    private String userId;
    private Long boardCnt;

    public CategoryDto(Category category) {
        this.name = category.getName();
        this.order = category.getOrder();
        this.userId = category.getUser().getId();
        this.num = category.getNum();
        this.boardCnt = (long) category.getBoards().size();
    }

    public CategoryDto(String name, String userId, long order) {
        this.name = name;
        this.userId = userId;
        this.order = order;
    }
}
