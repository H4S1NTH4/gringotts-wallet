package com.example.finance_tracker.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Currencies")
public class Currency {

    @Id
    String currencyId;
    String currencyName;
    Double currencyRate;
    String defaultCurrency = "USD";

    public Currency(String currencyName, Double currencyRate) {
        this.currencyName = currencyName;
        this.currencyRate = currencyRate;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

//    public void setDefaultCurrency(String defaultCurrency) {
//        this.defaultCurrency = defaultCurrency;
//    }
}
