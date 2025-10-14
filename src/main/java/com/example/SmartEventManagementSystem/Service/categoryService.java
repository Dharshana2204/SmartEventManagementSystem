package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Repository.CatergoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoryService {
    @Autowired
    private CatergoryRepository catergoryrepository;
    //Create category
    public  Category createCategory(Category category) {
        return catergoryrepository.save(category);
    }
    //Get Category
    public List<Category> getCategory() {
        return catergoryrepository.findAll();
    }
    //Get category by id
    public Category getCategoryById(Long id) {
        return catergoryrepository.findById(id).orElse(null);
    }
    //Update category
    public Category updateCategory(Long id,Category category) {
        return catergoryrepository.findById(id).map(existingCategory ->
        {
            existingCategory.setName(category.getName());
            return catergoryrepository.save(existingCategory);
        }).orElse(null);
    }
    //Delete Category
    public void deleteCategory(Long id)
        {
            catergoryrepository.deleteById(id);
        }

}
