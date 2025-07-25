package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.View;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface ViewRepository extends CrudRepository<View, Long> {
    @Query("""
            SELECT COUNT(v) from View v
            WHERE v.timestamp >= :startTime AND v.board.user.id=:userId
            """)
    long findViewCountByUserId(String userId, LocalDateTime startTime);
}
