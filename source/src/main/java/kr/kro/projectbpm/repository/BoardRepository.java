package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {
}
