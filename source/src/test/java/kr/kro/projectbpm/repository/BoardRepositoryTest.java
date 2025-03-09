package kr.kro.projectbpm.repository;

import jakarta.persistence.EntityManager;
import kr.kro.projectbpm.domain.Board;
import kr.kro.projectbpm.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserService userService;

    @Test
    @Order(1)
    void deleteAll() {
        boardRepository.deleteAll();
    }

    @Test
    @Order(2)
    void insertTest() {
        for (int i = 1; i <= 10; i++) {
            Board board = new Board("title "+i, "content "+i, userService.getUserById("aaa"));
            boardRepository.save(board);
        }
    }
}