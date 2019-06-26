package phattai.repository;

import org.springframework.data.repository.CrudRepository;

import phattai.model.User;

public interface UserRepository extends CrudRepository<User, String> {

}
