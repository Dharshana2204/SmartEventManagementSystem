package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.DTO.CategoryDTO;
import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Service.categoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO dto) {
        Category savedCategory = categoryService.createCategory(dto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/read/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId,
                                                   @Valid @RequestBody CategoryDTO dto) {
        Category updatedCategory = categoryService.updateCategory(categoryId, dto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully.", HttpStatus.OK);
    }
}
