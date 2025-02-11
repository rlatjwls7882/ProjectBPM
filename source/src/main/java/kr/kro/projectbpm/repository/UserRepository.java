package kr.kro.projectbpm.repository;

import kr.kro.projectbpm.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
