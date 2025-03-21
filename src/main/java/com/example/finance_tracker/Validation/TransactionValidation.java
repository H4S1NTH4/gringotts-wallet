package com.example.finance_tracker.Validation;

import com.example.finance_tracker.Entity.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TransactionValidation {

    public void validateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }
//        if (!StringUtils.hasText(transaction.getUserId())) {
//            throw new IllegalArgumentException("User ID is required.");
//        }
        if (transaction.getTransactionType() == null) {
            throw new IllegalArgumentException("Transaction Type is required.");
        }
//        if (transaction.getTransactionCategory() == null) {
//            throw new IllegalArgumentException("Transaction Category is required.");
//        }
        if (transaction.getTransactionDate() == null) {
            throw new IllegalArgumentException("Transaction Date is required.");
        }
        if (transaction.getTransactionAmount() == null || transaction.getTransactionAmount().doubleValue() <= 0) {
            throw new IllegalArgumentException("Transaction Amount must be greater than zero.");
        }
        if (!StringUtils.hasText(transaction.getTransactionDescription())) {
            throw new IllegalArgumentException("Transaction Description is required.");
        }
        if (Boolean.TRUE.equals(transaction.getRecurring()) && transaction.getRecurrencePattern() == null) {
            throw new IllegalArgumentException("Recurrence Pattern is required for recurring transactions.");
        }
    }
}
