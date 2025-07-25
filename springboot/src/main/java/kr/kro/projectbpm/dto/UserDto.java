package kr.kro.projectbpm.dto;

import kr.kro.projectbpm.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private Long totalViewCnt;
    private Long todayViewCnt;
    private long boardCnt;

    public UserDto(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.name = user.getName();
        this.totalViewCnt = user.getTotalViewCnt();
        this.boardCnt = user.getBoardList().size();
    }

    public void setViewCnt(long todayViewCnt) {
        this.todayViewCnt = todayViewCnt;
    }
}
