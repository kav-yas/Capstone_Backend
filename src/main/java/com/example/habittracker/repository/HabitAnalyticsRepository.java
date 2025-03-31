package com.example.habittracker.repository;

import com.example.habittracker.model.HabitTracking;
import com.example.habittracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitAnalyticsRepository extends JpaRepository<HabitTracking, Long> {

    // ✅ Get completed habits for a specific date
    List<HabitTracking> findByUserAndCompletionDate(User user, LocalDate completionDate);

    // ✅ Get completed habits within a date range (for weekly & monthly stats)
    @Query("SELECT h FROM HabitTracking h WHERE h.user = :user AND h.completionDate BETWEEN :startDate AND :endDate")
    List<HabitTracking> findCompletedHabitsInRange(User user, LocalDate startDate, LocalDate endDate);
}
