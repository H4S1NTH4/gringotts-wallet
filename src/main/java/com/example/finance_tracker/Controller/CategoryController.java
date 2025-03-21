package com.example.finance_tracker.Controller;

import com.example.finance_tracker.Entity.Category;
import com.example.finance_tracker.Entity.Transaction;
import com.example.finance_tracker.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
        @RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        try {
            return ResponseEntity.ok(categoryService.createCategory(category));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns validation error
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable String categoryId, @RequestBody Category category ){
        try {
            return ResponseEntity.ok(categoryService.updateCategory(categoryId, category));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns validation error
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred.");
        }

    }


}
