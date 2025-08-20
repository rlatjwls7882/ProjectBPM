package kr.kro.projectbpm.domain;

import jakarta.persistence.*;
import kr.kro.projectbpm.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categoryList = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Long totalViewCnt;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.totalViewCnt = 0L;
    }

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.password = userDto.getPassword();
        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.totalViewCnt = 0L;
    }

    public void read() {
        this.totalViewCnt++;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
