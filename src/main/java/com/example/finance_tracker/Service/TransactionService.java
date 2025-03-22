package com.example.finance_tracker.Service;

import com.example.finance_tracker.DTO.TransactionResponseDTO;
import com.example.finance_tracker.Entity.Budget;
import com.example.finance_tracker.Entity.Category;
import com.example.finance_tracker.Entity.Transaction;
import com.example.finance_tracker.Entity.User;
import com.example.finance_tracker.Repository.BudgetRepository;
import com.example.finance_tracker.Repository.CategoryRepository;
import com.example.finance_tracker.Repository.TransactionRepository;
import com.example.finance_tracker.Repository.UserRepository;
import com.example.finance_tracker.Validation.TransactionValidation;
import com.example.finance_tracker.Validation.UserValidation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserValidation userValidation;
    private final TransactionValidation transactionValidation;
    private final CategoryRepository categoryRepository;
    private final RecurringTransactionService recurringTransactionService;
    private final UserRepository userRepository;

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private CurrencyService currencyService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserValidation userValidation, TransactionValidation transactionValidation,
                              CategoryRepository categoryRepository, RecurringTransactionService recurringTransactionService, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userValidation =userValidation;
        this.transactionValidation = transactionValidation;
        this.categoryRepository = categoryRepository;
        this.recurringTransactionService = recurringTransactionService;
        this.userRepository = userRepository;
    }

    //Create a new transaction
    public Transaction createTransaction(Transaction transaction) {
        transactionValidation.validateTransaction(transaction);  // Validate transaction fields

        //get the current user from the token
        User user = userValidation.getUserFromToken();
        Category category = categoryRepository.findByName(transaction.getCategoryRef()).orElseThrow(() -> new IllegalArgumentException("Category not found"));

        transaction.setTransactionCategory(category);
        transaction.setUserId(user.getUserId());

        if (transaction.getRecurring() == null) {
            transaction.setRecurring(false); // Default to false if not provided
        }

        if(transaction.getRecurring()){
            LocalDateTime nextDueDate = recurringTransactionService.calculateNextDueDate(LocalDateTime.now(),transaction.getRecurrencePattern());
            transaction.setNextDueDate(nextDueDate);
        }

        transaction.setCreatedAt(LocalDateTime.now());

        if(transaction.getTransactionType()== Transaction.TransactionType.EXPENSE){
            //update budget according to transaction
            Optional<Budget> optionalBudget = budgetRepository.findByUserIdAndCategoryIdAndEndDateAfter(user.getUserId(), new ObjectId(category.getCategoryId()), transaction.getTransactionDate().toLocalDate());
            optionalBudget.ifPresent(
                    budget -> budgetService.updateBudgetSpend(budget, transaction.getTransactionAmount())
            );

        }



        return transactionRepository.save(transaction);
    }

    //service method to create automatic transactions from stripe wallet
    public void createAutomatedTransaction(Transaction transaction, String stripeCustomerId) {
        //transactionValidation.validateTransaction(transaction);  // Validate transaction fields
        System.out.println("Service called");
        //get the current user from the token
        User user = userRepository.findByStripeCustomerId(stripeCustomerId).orElseThrow(()-> new RuntimeException("User not found"));
        Category category = categoryRepository.findByName(transaction.getCategoryRef()).orElseThrow(() -> new RuntimeException("Category not found"));
        System.out.println("User found: " + user.getUserId());
        System.out.println("Category found: " + category.getCategoryId());
        transaction.setTransactionCategory(category);
        transaction.setUserId(user.getUserId());

//        if (transaction.getRecurring() == null) {
//            transaction.setRecurring(false); // Default to false if not provided
//        }
//        else{
//            LocalDateTime nextDueDate = recurringTransactionService.calculateNextDueDate(LocalDateTime.now(),transaction.getRecurrencePattern());
//            transaction.setNextDueDate(nextDueDate);
//        }


        //update budget according to transaction
        Optional<Budget> optionalBudget = budgetRepository.findByUserIdAndCategoryIdAndEndDateAfter(user.getUserId(), new ObjectId(category.getCategoryId()), transaction.getTransactionDate().toLocalDate());
        optionalBudget.ifPresent(budget -> budgetService.updateBudgetSpend(budget, transaction.getTransactionAmount()));

        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);

        System.out.println("Automatic Transaction created from Stripe Waller Transaction");
        transaction.printTransaction();
    }


    //retrieve all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    //Find a transaction by its ID.
    public TransactionResponseDTO getTransactionById(String transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with ID: " + transactionId));

        User user = userValidation.getUserFromToken();
        String userCurrency = user.getCurrency();

        BigDecimal convertedAmount = currencyService.convertCurrency(userCurrency, transaction.getTransactionAmount());
// Map the Transaction to TransactionResponseDTO
        return new TransactionResponseDTO(
                transaction.getTransactionId(),
                transaction.getUserId(),
                transaction.getTransactionType().name(),
                transaction.getTransactionCategory().getName(),
                transaction.getTransactionAmount(),
                convertedAmount,
                userCurrency,
                transaction.getTransactionDescription(),
                transaction.getCategoryRef()
        );

    }

    // Retrieve all transactions for a specific user.
    public List<Transaction> getTransactionsByUserId() {
        User user = userValidation.getUserFromToken();
        return transactionRepository.findByUserId(user.getUserId());
    }

    //Update an existing transaction.
    public Transaction updateTransaction(String transactionId, Transaction updatedTransaction) {
        transactionValidation.validateTransaction(updatedTransaction);  // Validate input

        return transactionRepository.findById(transactionId)
                .map(existingTransaction -> {
                    //existingTransaction.setUserId(updatedTransaction.getUserId());
                    existingTransaction.setTransactionType(updatedTransaction.getTransactionType());
                    existingTransaction.setTransactionCategory(updatedTransaction.getTransactionCategory());
                    existingTransaction.setTransactionDate(updatedTransaction.getTransactionDate());
                    existingTransaction.setTags(updatedTransaction.getTags());
                    existingTransaction.setRecurring(updatedTransaction.getRecurring() != null ? updatedTransaction.getRecurring() : false);
                    existingTransaction.setRecurrencePattern(updatedTransaction.getRecurrencePattern());
                    existingTransaction.setRecurringEndDate(updatedTransaction.getRecurringEndDate());
                    existingTransaction.setTransactionAmount(updatedTransaction.getTransactionAmount());
                    existingTransaction.setTransactionDescription(updatedTransaction.getTransactionDescription());
                    existingTransaction.setUpdatedAt(LocalDateTime.now());
                    return transactionRepository.save(existingTransaction);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));
    }

    //Delete a transaction by its ID.

    public void deleteTransaction(String transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new RuntimeException("Transaction not found with ID: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }

    public BigDecimal calculateUserTransactions(String transactionType) {
        User user = userValidation.getUserFromToken();
        List<Transaction> transactions = transactionRepository.findByUserIdAndTransactionType(user.getUserId(), Transaction.TransactionType.valueOf(transactionType));

        return transactions.stream()
                .map(Transaction::getTransactionAmount) // Extract the amount
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up amounts
    }

    public BigDecimal calculateAllTransactions(String transactionType) {
        User user = userValidation.getUserFromToken();
        List<Transaction> transactions = transactionRepository.findByTransactionType(Transaction.TransactionType.valueOf(transactionType));

        return transactions.stream()
                .map(Transaction::getTransactionAmount) // Extract the amount
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up amounts
    }



}
