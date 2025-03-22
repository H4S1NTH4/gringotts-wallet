package com.example.finance_tracker.Service;

import com.example.finance_tracker.DTO.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyService {

    @Value("${EXCHANGE_RATE_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double convertCurrency(String userCurrency, double amount) {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if (response != null && "success".equals(response.getResult())) {
            Map<String, Double> rates = response.getConversion_rates();
            if (rates.containsKey(userCurrency)) {
                return amount * rates.get(userCurrency);
            } else {
                throw new RuntimeException("Currency not found: " + userCurrency);
            }
        }
        throw new RuntimeException("Failed to fetch exchange rates");
    }
}
