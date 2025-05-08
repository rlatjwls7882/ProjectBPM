package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface BoardRepository extends CrudRepository<Board, Long> {
    long countBoardsByUserId(String id);
    Board findByBoardNum(Long boardNum);
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Board> findByUserIdAndTitleContainingIgnoreCase(String id, String title, Pageable pageable);
    @Query(
            value = """
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))
            GROUP BY b
            ORDER BY COALESCE(SUM(CASE WHEN v.timestamp >= :startTime THEN 1 ELSE 0 END), 0) DESC, b.inDate DESC
            """,
            countQuery = """
            SELECT COUNT(b) from Board b
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    Page<Board> findBoardsSortedByViews(LocalDateTime startTime, String query, Pageable pageable);
    @Query(
            value = """
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) AND b.user.id = :userId
            GROUP BY b
            ORDER BY COALESCE(SUM(CASE WHEN v.timestamp >= :startTime THEN 1 ELSE 0 END), 0) DESC, b.inDate DESC
            """,
            countQuery = """
            SELECT COUNT(b) from Board b
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) AND b.user.id = :userId
            """)
    Page<Board> findBoardsByUserIdSortedByViews(String userId, LocalDateTime startTime, String query, Pageable pageable);
    @Query(
            value = """
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE b.category.num = :categoryNum
            GROUP BY b
            ORDER BY COALESCE(SUM(CASE WHEN v.timestamp >= :startTime THEN 1 ELSE 0 END), 0) DESC, b.inDate DESC
            """,
            countQuery = """
            SELECT COUNT(b) from Board b
            WHERE b.category.num = :categoryNum
            """)
    Page<Board> findBoardsByCategoryNumSortedByViews(Long categoryNum, LocalDateTime startTime, Pageable pageable);
    @Query(
            value = """
            SELECT b from Board b
            LEFT JOIN View v ON v.board.boardNum = b.boardNum
            WHERE b.category.num = :categoryNum
            GROUP BY b
            ORDER BY b.inDate DESC
            """,
            countQuery = """
            SELECT COUNT(b) from Board b
            WHERE b.category.num = :categoryNum
            """)
    Page<Board> findBoardsByCategoryNum(Long categoryNum, Pageable pageable);
    Long countByCategoryNum(long categoryNum);
}
