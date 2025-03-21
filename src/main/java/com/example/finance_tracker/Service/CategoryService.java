package com.example.finance_tracker.Service;

import com.example.finance_tracker.Entity.Category;
import com.example.finance_tracker.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void deleteCategory(String categoryId){
        categoryRepository.deleteById(categoryId);
    }

    public Category updateCategory(String categoryId, Category categoryDetails) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        if(categoryDetails.getName() != null && !categoryDetails.getName().trim().isEmpty()){
            category.setName(categoryDetails.getName());
        }
        else {
            throw new IllegalArgumentException("Category name cannot be null.");
        }
        return categoryRepository.save(category);
    }
}
