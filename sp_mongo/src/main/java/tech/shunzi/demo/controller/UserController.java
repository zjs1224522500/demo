package tech.shunzi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shunzi.demo.model.User;
import tech.shunzi.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll()
    {
        return userService.findAll();
    }

    @PostMapping
    public User saveUser(@RequestBody User user)
    {
        return userService.saveUser(user);
    }

    @GetMapping("/single")
    public User getUser(String id)
    {
        return userService.findById(id);
    }
}
