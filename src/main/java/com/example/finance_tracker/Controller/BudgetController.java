package com.example.finance_tracker.Controller;

import com.example.finance_tracker.Entity.Budget;
import com.example.finance_tracker.Service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        return ResponseEntity.ok(budgetService.createBudget(budget));
    }

    // Get all budgets for a user
    @GetMapping("/user")
    public ResponseEntity<List<Budget>> getBudgetsByUserId() {
        return ResponseEntity.ok(budgetService.getBudgetsByUserId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable String id, @RequestBody Budget budget) {
        return ResponseEntity.ok(budgetService.updateBudget(id, budget));
    }

    // Delete a budget
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable String id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/budgetByCategory/{categoryId}")
    public ResponseEntity<Optional<Budget>> getBudgetByUserAndCategory(@PathVariable String categoryId) {

        return ResponseEntity.ok(budgetService.getBudgetForUserAndCategory(categoryId));
    }


//    // Check if user is exceeding the budget
//    @PostMapping("/check/{budgetId}")
//    public ResponseEntity<String> checkBudget(@PathVariable String budgetId, @RequestParam BigDecimal newExpense) {
//        Budget budget = budgetService.getBudgetById(budgetId); // ✅ FIXED Retrieval
//
//        // ✅ Update spentAmount before checking
//        budget.setSpentAmount(budget.getSpentAmount().add(newExpense));
//        budgetService.createOrUpdateBudget(budget);
//
//        boolean isOver = budgetService.isExceedingBudget(budget, newExpense);
//        return ResponseEntity.ok(isOver ? "You have exceeded your budget!" : "You are within budget.");
//    }
}


