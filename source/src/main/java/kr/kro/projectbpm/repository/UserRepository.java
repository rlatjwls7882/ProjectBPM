package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
