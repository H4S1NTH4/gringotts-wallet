package com.example.finance_tracker.Validation;

import com.example.finance_tracker.Entity.User;
import com.example.finance_tracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserValidation {

    @Autowired
    UserRepository userRepository;

    public User getUserFromToken(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
