package com.example.finance_tracker.Repository;

import com.example.finance_tracker.Entity.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GoalRepository  extends MongoRepository<Goal, String> {
    List<Goal> findGoalsByUserId(String userId);
}
