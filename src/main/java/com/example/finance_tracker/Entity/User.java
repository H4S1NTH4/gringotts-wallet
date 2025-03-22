package com.example.finance_tracker.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String username;
    private String stripeCustomerId;
    private String role;
    private  String currency;

    public User( String email, String firstName, String password, String lastName, String username,String role, String currency, String stripeCustomerId) {
        this.email = email;
        this.firstName = firstName;
        this.password = password;
        this.username = username;
        this.lastName = lastName;
        this.role = role;
        this.currency =currency;
        this.stripeCustomerId =stripeCustomerId;
    }

    public User(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }
}
