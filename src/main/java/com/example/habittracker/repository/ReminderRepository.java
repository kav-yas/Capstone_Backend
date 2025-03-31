package com.example.habittracker.repository;

import com.example.habittracker.model.Reminder;
import com.example.habittracker.model.User;
import com.example.habittracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    // ✅ Get all reminders for a specific user
    List<Reminder> findByUser(User user);

    // ✅ Get all reminders for a specific activity
    List<Reminder> findByActivity(Activity activity);

    // ✅ Get all pending (unsent) reminders for a user
    List<Reminder> findByUserAndSentFalse(User user);

    // ✅ Find unsent reminders for a specific user & activity
    Optional<Reminder> findByUserAndActivityAndSentFalse(User user, Activity activity);

    // ✅ Get reminders for a user on a specific date
    List<Reminder> findByUserAndReminderTimeBetween(User user, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
