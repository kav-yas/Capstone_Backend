package com.example.habittracker.service;

import com.example.habittracker.model.Reminder;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.ReminderRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Scheduled reminder (Runs every day at 8 AM)
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendReminders() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<Reminder> reminders = reminderRepository.findByUserAndReminderTimeBetween(user, startOfDay, endOfDay);
            for (Reminder reminder : reminders) {
                System.out.println("Reminder: " + user.getUsername() + ", don't forget to complete your habit!");
            }
        }
    }

    // ✅ Manually trigger reminders
    public void sendHabitReminders() {
        sendReminders();
    }

    // ✅ Get all reminders for a specific user
    public List<Reminder> getRemindersForUser(User user) {
        return reminderRepository.findByUser(user);
    }

    // ✅ Get pending reminders (not sent) for a user
    public List<Reminder> getPendingReminders(User user) {
        return reminderRepository.findByUserAndSentFalse(user);
    }

    // ✅ Get reminders for a specific date
    public List<Reminder> getRemindersForUserByDate(User user, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return reminderRepository.findByUserAndReminderTimeBetween(user, startOfDay, endOfDay);
    }

    // ✅ Mark a reminder as "sent"
    public boolean markReminderAsSent(Long reminderId) {
        Optional<Reminder> optionalReminder = reminderRepository.findById(reminderId);
        if (optionalReminder.isPresent()) {
            Reminder reminder = optionalReminder.get();
            reminder.setSent(true);
            reminderRepository.save(reminder);
            return true;
        }
        return false;
    }
}
