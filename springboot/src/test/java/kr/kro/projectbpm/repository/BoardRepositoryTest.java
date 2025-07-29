//package kr.kro.projectbpm.repository;
//
//import kr.kro.projectbpm.domain.Board;
//import kr.kro.projectbpm.domain.User;
//import kr.kro.projectbpm.service.UserService;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class BoardRepositoryTest {
//    @Autowired
//    BoardRepository boardRepository;
//    @Autowired
//    UserService userService;
//
//    @Test
//    @Order(1)
//    void deleteAll() {
//        boardRepository.deleteAll();
//    }
//
//    @Test
//    @Order(2)
//    void insertTest() throws Exception {
//        for (int i = 1; i <= 10; i++) {
//            Board board = new Board("title "+i, "content "+i, new User(userService.getUserById("aaa")));
//            boardRepository.save(board);
//        }
//    }
//}