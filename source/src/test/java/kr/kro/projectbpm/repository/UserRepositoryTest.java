package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.service.EncodeService;
import kr.kro.projectbpm.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EncodeService encodeService;

    @Order(1)
    @Test
    public void insertUser() {
        User user = new User();
        user.setId("aaa");
        user.setPassword(encodeService.encodePassword("bbb"));
        user.setName("A");
        user.setEmail("aaa@aaa.com");
        userRepository.save(user);
    }

    @Test
    @Order(2)
    public void findUserById() {
        User user = userRepository.findById("aaa").orElse(null);
        System.out.println("user = " + user);
    }
}