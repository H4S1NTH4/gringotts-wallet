package com.example.finance_tracker.Repository;

import com.example.finance_tracker.Entity.Category;
import com.example.finance_tracker.Entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    // Find all transactions by user ID
    List<Transaction> findByUserId(String userId);

    // Find transactions by type (INCOME or EXPENSE)
    List<Transaction> findByTransactionType(Transaction.TransactionType transactionType);

    // Find transactions by category (FOOD, TRANSPORT, ENTERTAINMENT)
    List<Transaction> findByTransactionCategory(Category transactionCategory);

    // Find recurring transactions
    List<Transaction> findByRecurringTrue();

    List<Transaction> findByUserIdAndTransactionType(String userId, Transaction.TransactionType transactionType);
}

