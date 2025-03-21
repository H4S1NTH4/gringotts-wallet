package com.example.finance_tracker.Validation;

import com.example.finance_tracker.Entity.Budget;
import com.example.finance_tracker.Entity.Category;
import com.example.finance_tracker.Entity.User;
import com.example.finance_tracker.Repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetValidation {

    @Autowired
    BudgetRepository budgetRepository;

    public void validateBudget(Budget budget, User user, Category category) {

        //check whether budget start and end dates correct
        if (budget.getStartDate().isAfter(budget.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        if (budget.getStartDate().isEqual(budget.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be the same as end date.");
        }

        //checks duplicate budgets for same category and overlapping date ranges

        Optional<Budget> existingBudget = budgetRepository.findOverlappingBudget(user.getUserId(), category, budget.getStartDate(), budget.getEndDate());

        if (existingBudget.isPresent()) {
            throw new RuntimeException("A budget for this category already exists in the given date range.");
        }
    }
}
