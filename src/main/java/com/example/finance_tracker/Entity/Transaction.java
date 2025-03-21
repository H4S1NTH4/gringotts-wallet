package com.example.finance_tracker.Entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "transactions")
public class Transaction {

    @Id
    private String transactionId;

    private String userId;

    private TransactionType transactionType;

    @DBRef
    private Category transactionCategory;

    private LocalDateTime transactionDate;

    private List<String> tags;

    private Boolean recurring; // Optional, so using Boolean instead of boolean

    private RecurrencePattern recurrencePattern;

    private BigDecimal transactionAmount;

    private String transactionDescription;

    private String categoryRef;

    private LocalDateTime nextDueDate;  // next transaction happening date

    private LocalDateTime recurringEndDate;

    @CreatedDate
    @Field(name = "createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Transaction() {
    }

    public Transaction(String userId, TransactionType transactionType, Category category,
                       LocalDateTime transactionDate, BigDecimal transactionAmount, String transactionDescription, String categoryRef, LocalDateTime recurringEndDate) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionCategory = category;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        this.categoryRef = categoryRef;
        this.recurringEndDate = recurringEndDate;
    }

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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Category getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(Category transactionCategory) {
        this.transactionCategory = transactionCategory;
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

    public RecurrencePattern getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(RecurrencePattern recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Enums
    public enum TransactionType {
        INCOME, EXPENSE
    }

    public enum RecurrencePattern {
        HOURLY, DAILY, WEEKLY, MONTHLY
    }
    public void printTransaction() {
        System.out.println("Transaction Details:");
        System.out.println("--------------------");
        System.out.println("Transaction ID      : " + transactionId);
        System.out.println("User ID             : " + userId);
        System.out.println("Transaction Type    : " + (transactionType != null ? transactionType : "N/A"));
        System.out.println("Category            : " + (transactionCategory != null ? transactionCategory.getName() : "N/A"));
        System.out.println("Category Ref        : " + (categoryRef != null ? categoryRef : "N/A"));
        System.out.println("Transaction Date    : " + (transactionDate != null ? transactionDate : "N/A"));
        System.out.println("Transaction Amount  : " + (transactionAmount != null ? "$" + transactionAmount : "N/A"));
        System.out.println("Description         : " + (transactionDescription != null ? transactionDescription : "N/A"));
        System.out.println("Tags               : " + (tags != null && !tags.isEmpty() ? String.join(", ", tags) : "No tags"));
        System.out.println("Recurring           : " + (recurring != null ? recurring : "N/A"));
        System.out.println("Recurrence Pattern  : " + (recurrencePattern != null ? recurrencePattern : "N/A"));
        System.out.println("Next Due Date       : " + (nextDueDate != null ? nextDueDate : "N/A"));
        System.out.println("Recurring End Date  : " + (recurringEndDate != null ? recurringEndDate : "N/A"));
        System.out.println("--------------------\n");
    }

}
