package com.example.finance_tracker.Service;

import com.example.finance_tracker.Entity.User;
import com.example.finance_tracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createUser(User user) {

        //email validation
        if(userRepository.findByEmail(user.getEmail()) != null) throw new IllegalArgumentException("Email is already in use");

        //username validation
        if(userRepository.findByUsername(user.getUsername()).isPresent()) throw new IllegalArgumentException("Username is already in use");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String userId, User userData) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        existingUser.setUsername(userData.getUsername());
//        existingUser.setEmail(userData.getEmail());
//        existingUser.setFirstName(userData.getFirstName());
//        existingUser.setLastName(userData.getLastName());
//        existingUser.setRole(userData.getRole());
//        existingUser.setCurrency(userData.getCurrency());
//        existingUser.setStripeCustomerId(userData.getStripeCustomerId());

        if (userData.getUsername() != null) {
            existingUser.setUsername(userData.getUsername());
        }
        if (userData.getEmail() != null) {
            existingUser.setEmail(userData.getEmail());
        }
        if (userData.getFirstName() != null) {
            existingUser.setFirstName(userData.getFirstName());
        }
        if (userData.getLastName() != null) {
            existingUser.setLastName(userData.getLastName());
        }
        if (userData.getRole() != null) {
            existingUser.setRole(userData.getRole());
        }
        if (userData.getCurrency() != null) {
            existingUser.setCurrency(userData.getCurrency());
        }
        if (userData.getStripeCustomerId() != null) {
            existingUser.setStripeCustomerId(userData.getStripeCustomerId());
        }


        return userRepository.save(existingUser);
    };

    public ResponseEntity<String> deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User with ID " + userId + " not found.");
        }

        userRepository.deleteById(userId);
        return ResponseEntity.ok("User with ID " + userId + " deleted successfully.");
    }


}
