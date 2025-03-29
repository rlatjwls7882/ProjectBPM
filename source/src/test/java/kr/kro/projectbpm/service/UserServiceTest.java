package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.Board;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class UserServiceTest {
    private final UserService userService;
    private final BoardService boardService;
    private final EncodeService encodeService;
    private final ViewService viewService;

    @Test
    void insert() {
        Board board = new Board();
    }
}