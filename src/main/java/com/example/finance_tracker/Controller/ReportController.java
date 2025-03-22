package com.example.finance_tracker.Controller;

import com.example.finance_tracker.Entity.Transaction;
import com.example.finance_tracker.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    TransactionService transactionService;


    //Get
    @GetMapping("user/transaction")
    public ResponseEntity<?> getUserTransactionSumByType(@RequestParam String transactionType) {
        try {
            BigDecimal transactionSum = transactionService.calculateUserTransactions(transactionType);
            return ResponseEntity.ok("Total amount for "+transactionType+ " = " +transactionSum);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Returns "Transaction not found" message
        }
    }
    //Get
    @GetMapping("all/transaction")
    public ResponseEntity<?> getAllTransactionSumByType(@RequestParam String transactionType) {
        try {
            BigDecimal transactionSum = transactionService.calculateAllTransactions(transactionType);
            return ResponseEntity.ok("Total transactions for "+transactionType+ " = " +transactionSum);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Returns "Transaction not found" message
        }
    }
}
