package kr.kro.projectbpm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_num")
    private Long boardNum;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 200000)
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Long viewCnt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date upDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<View> viewList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="category_num")
    private Category category;

    @Override
    public String toString() {
        return "Board{" +
                "bno=" + boardNum +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", inDate=" + inDate +
                ", upDate=" + upDate +
                ", user=" + user +
                '}';
    }

    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.viewCnt = 0L;
        this.inDate = new Date();
        this.upDate = new Date();
    }

    // for backup data
    public Board(String title, String content, User user, Long viewCnt, Date inDate, Date upDate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.viewCnt = viewCnt;
        this.inDate = inDate;
        this.upDate = upDate;
    }

    public void read() {
        this.viewCnt++;
        this.user.read();
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
        this.upDate = new Date();
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}