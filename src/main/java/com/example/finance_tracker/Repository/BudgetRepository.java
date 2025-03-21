package com.example.finance_tracker.Repository;

import com.example.finance_tracker.Entity.Budget;
import com.example.finance_tracker.Entity.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends MongoRepository<Budget, String> {
    List<Budget> findBudgetsByUserId(String userId);
    Optional<Budget> findByUserIdAndCategory(String userId, Category category);

    @Query("{ 'userId': ?0, 'category.$id': ?1, 'endDate': { $gt: ?2 } }")
    Optional<Budget> findByUserIdAndCategoryIdAndEndDateAfter(String userId, ObjectId categoryId, LocalDate transactionDate);

    @Query("""
    {'userId': ?0,
    'category.$id': ?1,
    '$or': [
            { 'startDate': { '$lte': ?3 }, 'endDate': { '$gte': ?2 } },
            { 'startDate': { '$lte': ?2 }, 'endDate': { '$gte': ?3 } }
        ]
    }
""")
    Optional<Budget> findOverlappingBudget(String userId, Category category, LocalDate startDate, LocalDate endDate);
}



