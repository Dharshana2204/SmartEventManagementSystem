package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Service.categoryService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class categoryController {
    @Autowired
    categoryService categoryservice;
    @PostMapping("/create")
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryservice.createCategory(category);
    }
    @GetMapping
    public List<Category> getCategory() {
        return categoryservice.getCategory();
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryservice.getCategoryById(id);
    }
    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryservice.updateCategory(id, category);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryservice.deleteCategory(id);
    }
}
