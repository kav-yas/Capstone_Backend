package com.example.habittracker.controller;

import com.example.habittracker.model.User;
import com.example.habittracker.repository.UserRepository;
import com.example.habittracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        String response = userService.registerUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Map<String, Object> response = userService.loginUser(username, password);

        if (response.containsKey("error")) {
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body((User) Map.of("error", "User not found!")));
    }

    @PatchMapping("/reminder-settings/{username}")
    public ResponseEntity<?> updateReminderSetting(@PathVariable String username, @RequestParam boolean enable) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found!"));
        }

        User user = userOptional.get();
        user.setReminderEnabled(enable);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Reminder setting updated successfully!"));
    }
}
