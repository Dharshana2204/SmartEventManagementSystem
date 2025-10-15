package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.DTO.CategoryDTO;
import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Repository.CatergoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoryService {

    @Autowired
    private CatergoryRepository categoryRepository;

    // CREATE CATEGORY
    public Category createCategory(CategoryDTO dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Category name already exists: " + dto.getName());
        }

        Category category = new Category();
        category.setName(dto.getName());
        return categoryRepository.save(category);
    }

    // GET ALL CATEGORIES
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // GET CATEGORY BY ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    // UPDATE CATEGORY
    public Category updateCategory(Long id, CategoryDTO dto) {
        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(dto.getName());
            return categoryRepository.save(existingCategory);
        }).orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    // DELETE CATEGORY
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
