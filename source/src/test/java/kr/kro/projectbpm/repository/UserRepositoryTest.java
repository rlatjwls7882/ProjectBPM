package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static kr.kro.projectbpm.EncodePassword.encodePassword;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Order(1)
    @Test
    public void insertUser() {
        User user = new User();
        user.setId("aaa");
        user.setPassword(encodePassword("bbb"));
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