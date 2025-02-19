package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.User;

public interface UserService {
    User getUser(String id);

    boolean existsUser(String id);

    void save(User user);
}
