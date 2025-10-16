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
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO dto) {
        String savedUser = userservice.createUser(dto);
        return new ResponseEntity(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userservice.getAllUsers();
        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        try {
            UserDTO userDTO = userservice.getUserById(id);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        UserDTO updatedUser = userservice.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userservice.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

}
