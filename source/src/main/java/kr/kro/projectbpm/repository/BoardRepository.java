package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.Board;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {
    List<Board> findByTitleContainingIgnoreCase(String title, Sort sort);
    List<Board> findByUserIdAndTitleContainingIgnoreCase(String id, String title, Sort sort);
    Board findByBoardNum(Long boardNum);
    @Query("""
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))
            GROUP BY b
            ORDER BY COALESCE(SUM(CASE WHEN v.timestamp >= :startTime THEN 1 ELSE 0 END), 0) DESC, b.inDate DESC
            """)
    List<Board> findBoardsSortedByViews(LocalDateTime startTime, String query);
    @Query("""
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) AND b.user.id = :userId
            GROUP BY b
            ORDER BY COALESCE(SUM(CASE WHEN v.timestamp >= :startTime THEN 1 ELSE 0 END), 0) DESC, b.inDate DESC
            """)
    List<Board> findBoardsByUserIdSortedByViews(String userId, LocalDateTime startTime, String query);
    @Query("""
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE b.category.num = :categoryNum
            GROUP BY b
            ORDER BY COALESCE(SUM(CASE WHEN v.timestamp >= :startTime THEN 1 ELSE 0 END), 0) DESC, b.inDate DESC
            """)
    List<Board> findBoardsByCategoryNumSortedByViews(Long categoryNum, LocalDateTime startTime);
    @Query("""
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE b.category.num = :categoryNum
            GROUP BY b
            ORDER BY b.inDate DESC
            """)
    List<Board> findBoardsByCategoryNum(Long categoryNum);
}
