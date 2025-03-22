package com.example.finance_tracker.DTO;

import java.math.BigDecimal;

public class TransactionResponseDTO {
    private String transactionId;
    private String userId;
    private String transactionType;
    private String transactionCategory;
    private BigDecimal transactionAmount;
    private BigDecimal convertedAmount;
    private String userCurrency;
    private String transactionDescription;
    private String categoryRef;

    // Constructor
    public TransactionResponseDTO(String transactionId, String userId, String transactionType, String transactionCategory,
                                  BigDecimal transactionAmount, BigDecimal convertedAmount, String userCurrency,
                                  String transactionDescription, String categoryRef) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionCategory = transactionCategory;
        this.transactionAmount = transactionAmount;
        this.convertedAmount = convertedAmount;
        this.userCurrency = userCurrency;
        this.transactionDescription = transactionDescription;
        this.categoryRef = categoryRef;
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

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getUserCurrency() {
        return userCurrency;
    }

    public void setUserCurrency(String userCurrency) {
        this.userCurrency = userCurrency;
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
}
