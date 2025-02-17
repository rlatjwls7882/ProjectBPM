package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUser(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean existsUser(String id) {
        return userRepository.existsById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
