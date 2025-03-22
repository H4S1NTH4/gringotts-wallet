package com.example.finance_tracker.Controller;

import com.example.finance_tracker.Entity.Category;
import com.example.finance_tracker.Service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @PostMapping("/convert")
    public ResponseEntity<?> convertCurrency(@RequestParam String userCurrency,@RequestParam Double amount){
        try {
            double convertedAmount = currencyService.convertCurrency(userCurrency, amount);
            return ResponseEntity.ok(convertedAmount);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }



}
