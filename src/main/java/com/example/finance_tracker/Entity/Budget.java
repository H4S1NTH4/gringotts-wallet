package com.example.finance_tracker.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "budgets")
public class Budget {
        @Id
        private String budgetId;

        private String userId;
        @DBRef
        private Category category;
        private BigDecimal amount;
        private BigDecimal spentAmount;
        private LocalDate startDate;
        private LocalDate endDate;
        private String categoryRef;

        // Constructor
        public Budget() {
                this.spentAmount = BigDecimal.ZERO; // Ensure spentAmount is initialized
        }

        // Getters & Setters
        public String getBudgetId() { return budgetId; }
        public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public Category getCategory() { return category; }
        public void setCategory(Category category) { this.category = category; }

        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }

        public BigDecimal getSpentAmount() { return spentAmount; }
        public void setSpentAmount(BigDecimal spentAmount) { this.spentAmount = spentAmount; }

        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

        public String getCategoryRef() {
                return categoryRef;
        }

        public void setCategoryRef(String categoryRef) {
                this.categoryRef = categoryRef;
        }
}


