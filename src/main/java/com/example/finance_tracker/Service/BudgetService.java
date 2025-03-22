package com.example.finance_tracker.Service;

import com.example.finance_tracker.Entity.Category;
import com.example.finance_tracker.Repository.CategoryRepository;
import com.example.finance_tracker.Validation.BudgetValidation;
import com.example.finance_tracker.Validation.UserValidation;
import com.example.finance_tracker.Entity.Budget;
import com.example.finance_tracker.Entity.User;
import com.example.finance_tracker.Repository.BudgetRepository;
import com.example.finance_tracker.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private UserValidation userValidation;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BudgetValidation budgetValidation;

    public Budget getBudgetById(String id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
    }

    public Budget createBudget(Budget budget) {

        if (budget.getSpentAmount() == null) {
            budget.setSpentAmount(BigDecimal.ZERO); // Ensure spentAmount is initialized
        }

        User user = userValidation.getUserFromToken();
        Category category = categoryRepository.findByName(budget.getCategoryRef()).orElseThrow(() -> new RuntimeException("Category not found"));
        //System.out.println("Authenticated username: " + user.getUsername() + "user ID:" + user.getUserId());

        budget.setUserId(user.getUserId());
        budget.setCategory(category);

        budgetValidation.validateBudget(budget,user,category); //validate budget

        return budgetRepository.save(budget);
    }

    // ✅ Get all budgets for a user
    public List<Budget> getBudgetsByUserId() {

        User user = userValidation.getUserFromToken();

        return budgetRepository.findBudgetsByUserId(user.getUserId());
    }

    // ✅ Update budget (Now updates spentAmount)
    public Budget updateBudget(String budgetId, Budget budgetDetails) {

        Budget budget = getBudgetById(budgetId);

//        BigDecimal newSpentAmount = budget.getSpentAmount().add(budgetDetails.getSpentAmount());
//        budget.setAmount(budgetDetails.getAmount());
//        budget.setStartDate(budgetDetails.getStartDate());
//        budget.setEndDate(budgetDetails.getEndDate());
//        budget.setSpentAmount(newSpentAmount);

        if (budgetDetails.getCategoryRef() != null) {
            Category category = categoryRepository.findByName(budgetDetails.getCategoryRef()).orElseThrow(() -> new RuntimeException("Category not found"));
            budget.setCategory(category);
            budget.setCategoryRef(budgetDetails.getCategoryRef());
        }

        if (budgetDetails.getAmount() != null) {
            budget.setAmount(budgetDetails.getAmount());
        }
        if (budgetDetails.getStartDate() != null) {
            budget.setStartDate(budgetDetails.getStartDate());
        }
        if (budgetDetails.getEndDate() != null) {
            budget.setEndDate(budgetDetails.getEndDate());
        }
        if (budgetDetails.getSpentAmount() != null) {
            BigDecimal newSpentAmount = budget.getSpentAmount().add(budgetDetails.getSpentAmount());
            budget.setSpentAmount(newSpentAmount);
        }

        return budgetRepository.save(budget);
    }

    public void updateBudgetSpend(Budget budget, BigDecimal transactionAmount) {

        BigDecimal newSpentAmount = budget.getSpentAmount().add(transactionAmount);
        budget.setSpentAmount(newSpentAmount);
        budgetRepository.save(budget);
        System.out.println("Budget updated");
    }

    //Delete budget by id
    public void deleteBudget(String id) {
        budgetRepository.deleteById(id);
    }

    //Check if user is exceeding budget (Now updates spentAmount)
    public boolean isExceedingBudget(Budget budget, BigDecimal newExpense) {
        BigDecimal newTotal = budget.getSpentAmount().add(newExpense);
        return newTotal.compareTo(budget.getAmount()) > 0;
    }

    public Optional<Budget> getBudgetForUserAndCategory(String categoryId) {
        // Call the repository method
        User user = userValidation.getUserFromToken();
        LocalDate transactionDate = LocalDate.now();
        // Convert categoryId to ObjectId
        ObjectId categoryObjectId = new ObjectId(categoryId);

        return budgetRepository.findByUserIdAndCategoryIdAndEndDateAfter(user.getUserId(), categoryObjectId, transactionDate);
    }

}


