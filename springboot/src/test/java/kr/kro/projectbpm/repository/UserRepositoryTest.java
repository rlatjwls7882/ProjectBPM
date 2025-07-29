//package kr.kro.projectbpm.repository;
//
//import kr.kro.projectbpm.domain.User;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class UserRepositoryTest {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Order(1)
//    @Test
//    public void insertUser() {
//        User user = new User("aaa", passwordEncoder.encode("aaa"), "A", "aaa@aaa.com");
//        userRepository.save(user);
//    }
//
//    @Test
//    @Order(2)
//    public void findUserById() {
//        User user = userRepository.findById("aaa").orElse(null);
//        System.out.println("user = " + user);
//    }
//}