package kr.kro.projectbpm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_num")
    private Long viewNum;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "board_num", nullable = false)
    private Board board;

    public View(Board board) {
        this.timestamp = LocalDateTime.now();
        this.board = board;
    }
}
