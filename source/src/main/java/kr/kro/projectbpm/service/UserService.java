package kr.kro.projectbpm.service;

import kr.kro.projectbpm.dto.UserDto;

public interface UserService {
    UserDto getUserById(String id);
    UserDto getUserByEmail(String email);
    String getUserNameById(String id);
    boolean existsById(String id);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    void save(UserDto userDto);
    void changePassword(UserDto userDto, String password);
}
