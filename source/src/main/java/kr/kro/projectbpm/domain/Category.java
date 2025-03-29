package kr.kro.projectbpm.domain;

import jakarta.persistence.*;
import kr.kro.projectbpm.dto.CategoryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "category_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(nullable = false)
    private String name;

    @Column(name = "`order`", nullable = false)
    private Long order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "category")
    private List<Board> boards = new ArrayList<>();

    @Override
    public String toString() {
        return "Category{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", user=" + user +
                '}';
    }

    public Category(CategoryDto categoryDto, User user) {
        this.name = categoryDto.getName();
        this.order = categoryDto.getOrder();
        this.user = user;
    }

    public void changeCategory(CategoryDto categoryDto) {
        this.order = categoryDto.getOrder();
        this.name = categoryDto.getName();
    }
}
