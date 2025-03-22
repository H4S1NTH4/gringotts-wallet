package com.example.finance_tracker.Service;

import com.example.finance_tracker.DTO.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyService {

    @Value("${EXCHANGE_RATE_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal convertCurrency(String userCurrency, BigDecimal amount) {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if (response != null && "success".equals(response.getResult())) {
            Map<String, Double> rates = response.getConversion_rates();
            if (rates.containsKey(userCurrency)) {
                BigDecimal rate = new BigDecimal(rates.get(userCurrency));
                return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP); // Round to 2 decimal places
            } else {
                throw new RuntimeException("Currency not found: " + userCurrency);
            }
        }
        throw new RuntimeException("Failed to fetch exchange rates");
    }
}
