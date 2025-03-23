package com.example.finance_tracker;

import com.example.finance_tracker.Entity.User;
import com.example.finance_tracker.Repository.UserRepository;
import com.example.finance_tracker.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUserId("1");
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole("USER");
        user.setCurrency("USD");
        user.setStripeCustomerId("stripe-id");
    }

    @Test
    public void testCreateUser_success() {
        // Mock behavior for findByEmail and findByUsername
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        // Call the method under test
        User createdUser = userService.createUser(user);

        // Assertions
        assertNotNull(createdUser);
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testCreateUser_emailAlreadyInUse() {
        // Mock behavior for findByEmail
        when(userRepository.findByEmail(any())).thenReturn(user);

        // Call the method under test and assert exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("Email is already in use", exception.getMessage());
    }

    @Test
    public void testCreateUser_usernameAlreadyInUse() {
        // Mock behavior for findByUsername
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        // Call the method under test and assert exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("Username is already in use", exception.getMessage());
    }

    @Test
    public void testUpdateUser_success() {
        // Mock behavior
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Modify the user data
        user.setFirstName("UpdatedFirstName");
        User updatedUser = userService.updateUser(user.getUserId(), user);

        // Assertions
        assertNotNull(updatedUser);
        assertEquals("UpdatedFirstName", updatedUser.getFirstName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUpdateUser_userNotFound() {
        // Mock behavior
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // Call the method under test and assert exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(user.getUserId(), user);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testDeleteUser_success() {
        // Mock behavior
        when(userRepository.existsById(any())).thenReturn(true);
        doNothing().when(userRepository).deleteById(any());

        // Call the method under test
        ResponseEntity<String> response = userService.deleteUser(user.getUserId());

        // Assertions
        assertNotNull(response);
        assertEquals("User with ID " + user.getUserId() + " deleted successfully.", response.getBody());
        verify(userRepository).deleteById(any());
    }

    @Test
    public void testDeleteUser_userNotFound() {
        // Mock behavior
        when(userRepository.existsById(any())).thenReturn(false);

        // Call the method under test and assert exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(user.getUserId());
        });

        assertEquals("User with ID " + user.getUserId() + " not found.", exception.getMessage());
    }
}
