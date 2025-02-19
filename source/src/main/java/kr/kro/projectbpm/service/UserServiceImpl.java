package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsUser(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}