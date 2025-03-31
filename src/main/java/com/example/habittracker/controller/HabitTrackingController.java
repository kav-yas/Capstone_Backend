package com.example.habittracker.controller;

import com.example.habittracker.service.HabitTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit")
@CrossOrigin
public class HabitTrackingController {

    @Autowired
    private HabitTrackingService habitTrackingService;

    // ✅ Mark habit as completed
    @PostMapping("/complete/{username}/{activityId}")
    public ResponseEntity<?> markHabitCompletion(@PathVariable String username, @PathVariable Long activityId) {
        String response = habitTrackingService.markHabitCompletion(username, activityId);
        if (response.startsWith("Error:")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    // ✅ Get current streak of a user
    @GetMapping("/streak/{username}")
    public ResponseEntity<?> getUserStreak(@PathVariable String username) {
        Integer streak = habitTrackingService.getUserStreak(username);
        return ResponseEntity.ok(streak);
    }

    // ✅ Get habit history for a user
    @GetMapping("/history/{username}")
    public ResponseEntity<?> getUserHabitHistory(@PathVariable String username) {
        return ResponseEntity.ok(habitTrackingService.getUserHabitHistory(username));
    }

    // ✅ Get habits completed today or past 'n' days
    @GetMapping("/today/{username}/{days}")
    public ResponseEntity<?> getUserHabitsForToday(@PathVariable String username, @PathVariable int days) {
        return ResponseEntity.ok(habitTrackingService.getHabitsForToday(username, days));
    }
}
