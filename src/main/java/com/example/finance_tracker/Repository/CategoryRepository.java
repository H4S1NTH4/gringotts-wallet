package com.example.finance_tracker.Repository;

import com.example.finance_tracker.Entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    //find category by name
    Optional<Category> findByName(String name);

}
