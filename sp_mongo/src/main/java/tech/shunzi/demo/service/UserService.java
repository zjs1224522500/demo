package tech.shunzi.demo.service;

import tech.shunzi.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User saveUser(User user);

    User findById(String id);

    List<User> findByUserName(String userName);
}
