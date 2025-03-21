package com.example.finance_tracker.Service;
import com.example.finance_tracker.Entity.Transaction;
import com.example.finance_tracker.Repository.CategoryRepository;
import com.example.finance_tracker.Repository.TransactionRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecurringTransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Method to calculate the next due date based on recurrence pattern
    public LocalDateTime calculateNextDueDate(LocalDateTime currentDate, Transaction.RecurrencePattern recurrencePattern) {
        return switch (recurrencePattern) {
            case HOURLY -> currentDate.plusHours(1);
            case DAILY -> currentDate.plusDays(1);
            case WEEKLY -> currentDate.plusWeeks(1);
            case MONTHLY -> currentDate.plusMonths(1);
            default -> throw new IllegalArgumentException("Unknown recurrence pattern");
        };
    }

    // Scheduled task to process recurring transactions
    @Scheduled(cron = "0 0 * * * *")  // Run in every hour  // second minute hour day month day_of_week  *- evry  , 0 -0th
    public void processRecurringTransactions() {
        // Fetch transactions that are marked as recurring
        List<Transaction> recurringTransactions = transactionRepository.findByRecurringTrue();

        for (Transaction recurringTransaction : recurringTransactions) {
            if (recurringTransaction.getNextDueDate() != null  && recurringTransaction.getNextDueDate().isBefore(LocalDateTime.now())) {
                // Create the recurring transaction
                createRecurringTransaction(recurringTransaction);

                // Update the next due date for the transaction
                LocalDateTime nextDueDate = calculateNextDueDate(LocalDateTime.now(), recurringTransaction.getRecurrencePattern());
                if (recurringTransaction.getRecurringEndDate() != null && nextDueDate.isAfter(recurringTransaction.getRecurringEndDate())) {
                    recurringTransaction.setRecurring(false);
                    recurringTransaction.setNextDueDate(null);
                }else{
                    recurringTransaction.setNextDueDate(nextDueDate);

                }
                transactionRepository.save(recurringTransaction);
            }
        }
    }

    // Method to create a new transaction based on the recurring transaction
    private void createRecurringTransaction(Transaction recurringTransaction) {
        Transaction newTransaction = new Transaction();
        newTransaction.setUserId(recurringTransaction.getUserId());
        newTransaction.setTransactionType(recurringTransaction.getTransactionType());
        newTransaction.setTransactionCategory(recurringTransaction.getTransactionCategory());
        newTransaction.setTransactionDate(LocalDateTime.now());  // Set current date as the transaction date
        newTransaction.setTransactionAmount(recurringTransaction.getTransactionAmount());
        newTransaction.setTransactionDescription(recurringTransaction.getTransactionDescription());
        newTransaction.setCategoryRef(recurringTransaction.getCategoryRef());
        newTransaction.setRecurring(false);  // This is a one-time transaction, not recurring
        newTransaction.setRecurrencePattern(recurringTransaction.getRecurrencePattern());
        newTransaction.setNextDueDate(null);  // No need to track next due date for the one-time transaction

        transactionRepository.save(newTransaction);  // Save the new transaction
    }
}
