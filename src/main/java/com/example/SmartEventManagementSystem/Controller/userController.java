package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Repository.UserRepository;
import com.example.SmartEventManagementSystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserService userservice;
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userservice.getAllUsers(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public User createuser(@Valid @RequestBody User user) {
        return userservice.createuser(user);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userservice.getUserById(id);
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        return userservice.updateUser(id,user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userservice.deleteuser(id);
    }



}
