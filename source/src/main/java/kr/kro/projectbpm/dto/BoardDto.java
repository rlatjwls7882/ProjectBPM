package kr.kro.projectbpm.dto;

import kr.kro.projectbpm.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class BoardDto {
    private Long boardNum;
    private String title;
    private String content;
    private Long viewCnt;
    private Date inDate;
    private Date upDate;
    private String userId;
    private String userName;
    private String type;
    private String tabTitle;
    private String pushButton;
    private String categoryName;
    private Long categoryNum;

    public BoardDto(Board board) {
        this.boardNum = board.getBoardNum();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCnt = board.getViewCnt();
        this.inDate = board.getInDate();
        this.upDate = board.getUpDate();
        this.userId = board.getUser().getId();
        this.userName = board.getUser().getName();
        try {
            this.categoryName = board.getCategory().getName();
            this.categoryNum = board.getCategory().getNum();
        } catch (Exception e) {
            this.categoryName = "없음";
            this.categoryNum = 0L;
        }
    }

    public BoardDto(String id, String userName) { // write
        this.userId = id;
        this.type = "write";
        this.userName = userName;
        this.boardNum = 0L;
        this.viewCnt = 0L;
        this.title = "";
        this.content = "";
        this.inDate = new Date();
        this.upDate = new Date();
        this.tabTitle = "글 쓰기";
        this.pushButton = "제출하기";
        this.categoryName = "없음";
        this.categoryNum = 0L;
    }

    public void setEdit() {
        this.type = "edit";
        this.tabTitle = "글 수정하기";
        this.pushButton = "변경하기";
    }
}
