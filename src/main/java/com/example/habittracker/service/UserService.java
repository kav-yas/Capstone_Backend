//user service
package com.example.habittracker.service;

import com.example.habittracker.model.User;
import com.example.habittracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user) {
        // Check if username, email, or phone already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists!";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already registered!";
        }
        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            return "Phone number already registered!";
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    public Map<String, Object> loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Map<String, Object> response = new HashMap<>();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                response.put("message", "Login successful!");
                response.put("userId", user.getId());
                response.put("username", user.getUsername());
                return response;
            } else {
                response.put("error", "Incorrect password!");
                return response;
            }
        } else {
            response.put("error", "User not found!");
            return response;
        }
    }

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }
}
