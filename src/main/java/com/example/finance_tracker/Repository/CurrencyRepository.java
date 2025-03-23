package com.example.finance_tracker.Repository;

import com.example.finance_tracker.Entity.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
    Optional<Currency> findByCurrencyName(String currencyName);

}
