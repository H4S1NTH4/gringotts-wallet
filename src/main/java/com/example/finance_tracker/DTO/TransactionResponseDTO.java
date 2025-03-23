package com.example.finance_tracker.DTO;

import com.example.finance_tracker.Entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionResponseDTO {
    private String transactionId;
    private String userId;
    private String transactionType;
    private String transactionCategory;
    private LocalDateTime transactionDate;
    private List<String> tags;
    private Boolean recurring; // Optional, so using Boolean instead of boolean
    private Transaction.RecurrencePattern recurrencePattern;
    private BigDecimal transactionAmount;
    //private BigDecimal convertedAmount;
    //private String userCurrency;
    private String transactionDescription;
    private String categoryRef;
    private LocalDateTime nextDueDate;  // next transaction happening date
    private LocalDateTime recurringEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor
    public TransactionResponseDTO(String transactionId, String userId, String transactionType, String transactionCategory,
                                  LocalDateTime transactionDate, List<String> tags, Boolean recurring, Transaction.RecurrencePattern
                                          recurrencePattern, BigDecimal transactionAmount, String transactionDescription, String categoryRef, LocalDateTime nextDueDate, LocalDateTime recurringEndDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionCategory = transactionCategory;
        this.transactionDate = transactionDate;
        this.tags = tags;
        this.recurring = recurring;
        this.recurrencePattern = recurrencePattern;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        this.categoryRef = categoryRef;
        this.nextDueDate = nextDueDate;
        this.recurringEndDate = recurringEndDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public TransactionResponseDTO() {}

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

//    public BigDecimal getConvertedAmount() {
//        return convertedAmount;
//    }
//
//    public void setConvertedAmount(BigDecimal convertedAmount) {
//        this.convertedAmount = convertedAmount;
//    }
//
//    public String getUserCurrency() {
//        return userCurrency;
//    }
//
//    public void setUserCurrency(String userCurrency) {
//        this.userCurrency = userCurrency;
//    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getCategoryRef() {
        return categoryRef;
    }

    public void setCategoryRef(String categoryRef) {
        this.categoryRef = categoryRef;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Boolean getRecurring() {
        return recurring;
    }

    public void setRecurring(Boolean recurring) {
        this.recurring = recurring;
    }

    public Transaction.RecurrencePattern getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(Transaction.RecurrencePattern recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }

    public LocalDateTime getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDateTime nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public LocalDateTime getRecurringEndDate() {
        return recurringEndDate;
    }

    public void setRecurringEndDate(LocalDateTime recurringEndDate) {
        this.recurringEndDate = recurringEndDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
