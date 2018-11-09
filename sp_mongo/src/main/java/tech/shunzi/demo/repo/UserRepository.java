package tech.shunzi.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.shunzi.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {


}
