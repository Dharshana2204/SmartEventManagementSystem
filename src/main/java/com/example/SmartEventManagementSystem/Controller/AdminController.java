package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Repository.CatergoryRepository;
import com.example.SmartEventManagementSystem.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CatergoryRepository catergoryRepository;
    private final UserRepository userRepository;

    public AdminController(CatergoryRepository catergoryRepository, UserRepository userRepository) {
        this.catergoryRepository = catergoryRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category saved = catergoryRepository.save(category);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> listCategories() {
        return ResponseEntity.ok(catergoryRepository.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
