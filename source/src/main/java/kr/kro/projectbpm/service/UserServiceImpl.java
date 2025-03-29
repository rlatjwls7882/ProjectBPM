package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.dto.UserDto;
import kr.kro.projectbpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EncodeService encodeService;

    @Override
    public UserDto getUserById(String id) {
        try {
            return new UserDto(userRepository.findUserById(id));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        try {
            return new UserDto(userRepository.findUserByEmail(email));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getUserNameById(String id) {
        return getUserById(id).getName();
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
    public void save(UserDto userDto) {
        userRepository.save(new User(userDto));
        changePassword(userDto, userDto.getPassword()); // 비밀번호 변환
    }

    @Override
    public void changePassword(UserDto userDto, String password) {
        User user = userRepository.findUserById(userDto.getId());
        user.changePassword(encodeService.encodePassword(password));
    }
}