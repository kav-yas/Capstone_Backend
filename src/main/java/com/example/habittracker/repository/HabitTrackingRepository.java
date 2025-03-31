package com.example.habittracker.repository;

import com.example.habittracker.model.HabitTracking;
import com.example.habittracker.model.User;
import com.example.habittracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitTrackingRepository extends JpaRepository<HabitTracking, Long> {

    // ✅ Get all habit tracking entries for a user
    List<HabitTracking> findByUser(User user);

    // ✅ Get the most recent habit tracking entry for a user and activity
    Optional<HabitTracking> findTopByUserAndActivityOrderByCompletionDateDesc(User user, Activity activity);

    // ✅ Get all habit tracking entries for a user, sorted by most recent completion date
    List<HabitTracking> findByUserOrderByCompletionDateDesc(User user);

    Optional<HabitTracking> findByUserAndActivityAndCompletionDate(User user, Activity activity, LocalDate completionDate);


    // ✅ Get habits completed today for a user
    List<HabitTracking> findByUserAndCompletionDate(User user, LocalDate completionDate);

    // ✅ Get habits completed in a date range (e.g., last 'n' days)
    List<HabitTracking> findByUserAndCompletionDateBetweenOrderByCompletionDateDesc(User user, LocalDate startDate, LocalDate endDate);

    // ✅ Fix: Get habits completed **after** a specific date
    List<HabitTracking> findByUserAndCompletionDateAfterOrderByCompletionDateDesc(User user, LocalDate completionDate);
}