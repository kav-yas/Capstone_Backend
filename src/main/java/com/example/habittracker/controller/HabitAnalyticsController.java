package com.example.habittracker.controller;

import com.example.habittracker.model.HabitTracking;
import com.example.habittracker.service.HabitAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/habit/analytics")
@CrossOrigin
public class HabitAnalyticsController {

    @Autowired
    private HabitAnalyticsService habitAnalyticsService;

    // ✅ Get habit completion stats (daily, weekly, monthly)
    @GetMapping("/completion-stats/{username}")
    public ResponseEntity<Map<String, Integer>> getCompletionStats(@PathVariable String username) {
        Map<String, Integer> stats = habitAnalyticsService.getHabitCompletionStats(username);
        return ResponseEntity.ok(stats);
    }

    // ✅ Get all completed habits for a user
    @GetMapping("/completed/{username}")
    public ResponseEntity<List<HabitTracking>> getAllCompletedHabits(@PathVariable String username) {
        List<HabitTracking> habits = habitAnalyticsService.getAllCompletedHabits(username);
        return ResponseEntity.ok(habits);
    }
}
