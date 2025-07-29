package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.dto.UserDto;
import kr.kro.projectbpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${crypto.aes.key}")
    private String aes_key;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findUserById(id);
        if(user==null) throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다.");
        return new UserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user==null) throw new IllegalArgumentException("해당 이메일은 존재하지 않습니다.");
        return new UserDto(user);
    }

    @Override
    public String getUserName(String id) {
        UserDto userDto = getUserById(id);
        if(userDto==null) throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다.");
        return userDto.getName();
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void save(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getName(),
                userDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public void checkPassword(UserDto userDto, String password) {
        if(!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void changePassword(UserDto userDto, String password) {
        User user = userRepository.findUserById(userDto.getId());
        if(user==null) {
            throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다.");
        }
        user.changePassword(passwordEncoder.encode(password));
    }
}