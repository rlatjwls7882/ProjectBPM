package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.Board;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {
    List<Board> findByTitleContainingIgnoreCase(Sort sort, String title);
    Board findByBoardNum(Long boardNum);
    @Query("""
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))
            AND (v.timestamp >= :startTime OR v.timestamp IS NULL)
            GROUP BY b
            ORDER BY COALESCE(COUNT(v), 0) DESC, b.inDate DESC
            """)
    List<Board> findBoardsSortedByViews(LocalDateTime startTime, String query);
}
