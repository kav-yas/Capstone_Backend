package com.example.habittracker.controller;

import com.example.habittracker.model.Reminder;
import com.example.habittracker.model.User;
import com.example.habittracker.service.ReminderService;
import com.example.habittracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habit/reminders")
@CrossOrigin
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private UserService userService;

    // ✅ Manually trigger reminders (for testing)
    @GetMapping("/manual")
    public ResponseEntity<String> triggerRemindersManually() {
        reminderService.sendHabitReminders();
        return ResponseEntity.ok("Reminders triggered manually!");
    }

    // ✅ Get all reminders for a user
    @GetMapping("/{username}")
    public ResponseEntity<?> getRemindersForUser(@PathVariable String username) {
        User user = userService.findByUsername(username); // Direct User object
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found!");
        }
        return ResponseEntity.ok(reminderService.getRemindersForUser(user));
    }


    // ✅ Get pending (unsent) reminders
    @GetMapping("/pending/{username}")
    public ResponseEntity<?> getPendingRemindersForUser(@PathVariable String username) {
        User user = userService.findByUsername(username); // Direct User object
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found!");
        }
        return ResponseEntity.ok(reminderService.getPendingReminders(user));
    }


    // ✅ Mark a reminder as "sent"
    @PatchMapping("/mark-sent/{reminderId}")
    public ResponseEntity<String> markReminderAsSent(@PathVariable Long reminderId) {
        boolean updated = reminderService.markReminderAsSent(reminderId);
        if (updated) {
            return ResponseEntity.ok("Reminder marked as sent!");
        }
        return ResponseEntity.badRequest().body("Reminder not found!");
    }
}
