package com.example.habittracker.service;

import com.example.habittracker.model.HabitTracking;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.HabitAnalyticsRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HabitAnalyticsService {

    @Autowired
    private HabitAnalyticsRepository habitAnalyticsRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get habit completion stats (daily, weekly, monthly)
    public Map<String, Integer> getHabitCompletionStats(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }
        User user = userOptional.get();
        LocalDate today = LocalDate.now();

        // Daily stats
        int dailyCount = habitAnalyticsRepository.findByUserAndCompletionDate(user, today).size();

        // Weekly stats
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        int weeklyCount = habitAnalyticsRepository.findCompletedHabitsInRange(user, startOfWeek, today).size();

        // Monthly stats
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        int monthlyCount = habitAnalyticsRepository.findCompletedHabitsInRange(user, startOfMonth, today).size();

        // Return stats as a map
        Map<String, Integer> stats = new HashMap<>();
        stats.put("daily", dailyCount);
        stats.put("weekly", weeklyCount);
        stats.put("monthly", monthlyCount);
        return stats;
    }

    // ✅ Get all completed habits for a user (for displaying history)
    public List<HabitTracking> getAllCompletedHabits(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }
        return habitAnalyticsRepository.findByUserAndCompletionDate(userOptional.get(), LocalDate.now());
    }
}
