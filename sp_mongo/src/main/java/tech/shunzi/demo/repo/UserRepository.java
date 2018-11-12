package tech.shunzi.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.shunzi.demo.model.User;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUserName(String userName);

}
