package com.example.finance_tracker;

import com.example.finance_tracker.Controller.TransactionController;
import com.example.finance_tracker.DTO.TransactionResponseDTO;
import com.example.finance_tracker.Entity.Transaction;
import com.example.finance_tracker.Service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;
    private TransactionResponseDTO transactionResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transaction = new Transaction();
        transactionResponseDTO = new TransactionResponseDTO();
        transaction.setTransactionId("1");
        transaction.setTransactionAmount(BigDecimal.valueOf(100.0));
        transaction.setTransactionDescription("Test Transaction");
        transaction.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testCreateTransaction_Success() {
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        ResponseEntity<?> response = transactionController.createTransaction(transaction);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testCreateTransaction_InvalidInput() {
        when(transactionService.createTransaction(any(Transaction.class))).thenThrow(new IllegalArgumentException("Invalid transaction data"));

        ResponseEntity<?> response = transactionController.createTransaction(transaction);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid transaction data", response.getBody());
    }

    @Test
    void testGetAllTransactions() {
        List<Transaction> transactions = Arrays.asList(transaction);
        when(transactionService.getAllTransactions()).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactions();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transactions, response.getBody());
    }

    @Test
    void testGetTransactionById_Found() {
        when(transactionService.getTransactionById("1")).thenReturn(transactionResponseDTO);

        ResponseEntity<?> response = transactionController.getTransactionById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(transactionService.getTransactionById("1")).thenThrow(new RuntimeException("Transaction not found with ID: 1"));

        ResponseEntity<?> response = transactionController.getTransactionById("1");

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Transaction not found with ID: 1", response.getBody());
    }

    @Test
    void testUpdateTransaction_Success() {
        when(transactionService.updateTransaction(eq("1"), any(Transaction.class))).thenReturn(transaction);

        ResponseEntity<?> response = transactionController.updateTransaction("1", transaction);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testUpdateTransaction_NotFound() {
        when(transactionService.updateTransaction(eq("1"), any(Transaction.class)))
                .thenThrow(new RuntimeException("Transaction not found with ID: 1"));

        ResponseEntity<?> response = transactionController.updateTransaction("1", transaction);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Transaction not found with ID: 1", response.getBody());
    }

    @Test
    void testDeleteTransaction_Success() {
        doNothing().when(transactionService).deleteTransaction("1");

        ResponseEntity<?> response = transactionController.deleteTransaction("1");

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTransaction_NotFound() {
        doThrow(new RuntimeException("Transaction not found with ID: 1")).when(transactionService).deleteTransaction("1");

        ResponseEntity<?> response = transactionController.deleteTransaction("1");

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Transaction not found with ID: 1", response.getBody());
    }

    @Test
    void testGetTransactionsByUserId() {
        List<Transaction> transactions = Arrays.asList(transaction);
        when(transactionService.getTransactionsByUserId()).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByUserId();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transactions, response.getBody());
    }
}
