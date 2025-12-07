package com.jobFinder.api.Controller;

import com.jobFinder.api.Pojo.Entity.User;
import com.jobFinder.api.Pojo.Input.UserInput;
import com.jobFinder.api.Repository.UserRepository;
import com.jobFinder.api.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id){
        return userRepository.findUserById(id);
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public User addUser(@RequestBody UserInput user){
        return userService.createUser(user);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody UserInput userInput,@PathVariable int id) {
        return userService.updateUser(userInput,id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}
