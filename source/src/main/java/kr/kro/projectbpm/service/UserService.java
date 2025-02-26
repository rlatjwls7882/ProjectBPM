package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.User;

public interface UserService {
    User getUserById(String id);

    User getUserByEmail(String email);

    boolean existsById(String id);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    void save(User user);
}
