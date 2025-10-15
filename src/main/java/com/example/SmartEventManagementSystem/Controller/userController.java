package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.DTO.UserDTO;
import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Repository.UserRepository;
import com.example.SmartEventManagementSystem.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class userController {
    @Autowired
    private UserService userservice;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO dto) {
        User savedUser = userservice.createUser(dto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userservice.getAllUsers(), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userservice.getAllUsers());
//    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userservice.getUserById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userservice.updateUser(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userservice.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

}
