package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.dto.UserDto;
import kr.kro.projectbpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 서비스 구현 클래스입니다.
 * 사용자 정보를 조회, 저장, 수정하는 기능을 제공합니다.
 * @see UserRepository
 * @see PasswordEncoder
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${crypto.aes.key}")
    private String aes_key;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 아이디로 사용자 정보를 조회합니다.
     * @param id 사용자 아이디
     * @return UserDto 사용자 정보
     * @throws IllegalArgumentException 해당 아이디가 존재하지 않을 경우 예외 발생
     */
    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findUserById(id);
        if(user==null) throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다.");
        return new UserDto(user);
    }

    /**
     * 사용자 이메일로 사용자 정보를 조회합니다.
     * @param email 사용자 이메일
     * @return UserDto 사용자 정보
     * @throws IllegalArgumentException 해당 이메일이 존재하지 않을 경우 예외 발생
     */
    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user==null) throw new IllegalArgumentException("해당 이메일은 존재하지 않습니다.");
        return new UserDto(user);
    }

    /**
     * 사용자 아이디로 사용자 이름을 조회합니다.
     * @param id 사용자 아이디
     * @return UserDto 사용자 정보
     * @throws IllegalArgumentException 해당 아이디가 존재하지 않을 경우 예외 발생
     */
    @Override
    public String getUserName(String id) {
        UserDto userDto = getUserById(id);
        if(userDto==null) throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다.");
        return userDto.getName();
    }

    /**
     * 사용자 아이디가 존재하는지 확인합니다.
     * @param id 사용자 아이디
     * @return boolean 아이디 존재 여부
     */
    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    /**
     * 사용자 이름이 존재하는지 확인합니다.
     * @param name 사용자 이름
     * @return boolean 이름 존재 여부
     */
    @Override
    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    /**
     * 사용자 이메일이 존재하는지 확인합니다.
     * @param email 사용자 이메일
     * @return boolean 이메일 존재 여부
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * 사용자 정보를 저장합니다.
     * @param userDto 사용자 정보
     */
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

    /**
     * 사용자 비밀번호를 확인합니다.
     * @param userDto 사용자 정보
     * @param password 입력 비밀번호
     * @throws IllegalArgumentException 비밀번호가 일치하지 않을 경우 예외 발생
     */
    @Override
    public void checkPassword(UserDto userDto, String password) {
        if(!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 사용자 비밀번호를 변경합니다.
     * @param userDto 사용자 정보
     * @param password 새 비밀번호
     * @throws IllegalArgumentException 해당 아이디가 존재하지 않을 경우 예외 발생
     */
    @Override
    public void changePassword(UserDto userDto, String password) {
        User user = userRepository.findUserById(userDto.getId());
        if(user==null) throw new IllegalArgumentException("해당 아이디는 존재하지 않습니다.");
        user.changePassword(passwordEncoder.encode(password));
    }
}