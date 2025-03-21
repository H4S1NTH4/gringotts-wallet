package com.example.finance_tracker.Controller;

import com.example.finance_tracker.Entity.Transaction;
import com.example.finance_tracker.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.ok(createdTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns validation error
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred when creating transaction.");
        }
    }

    /**
     * Retrieve all transactions.
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    /**
     * Retrieve a transaction by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        try {
            Transaction transaction = transactionService.getTransactionById(id);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Returns "Transaction not found" message
        }
    }

    /**
     * Update an existing transaction.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable String id, @RequestBody Transaction updatedTransaction) {
        try {
            Transaction transaction = transactionService.updateTransaction(id, updatedTransaction);
            return ResponseEntity.ok(transaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Validation error
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Transaction not found
        }
    }

    /**
     * Delete a transaction by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Transaction not found
        }
    }

    /**
     * Retrieve all transactions for a specific user.
     */
    @GetMapping("/user")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId() {
        List<Transaction> transactions = transactionService.getTransactionsByUserId();
        return ResponseEntity.ok(transactions);
    }
}
