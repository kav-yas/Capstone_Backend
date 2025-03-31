package com.example.habittracker.service;

import com.example.habittracker.model.Activity;
import com.example.habittracker.model.HabitTracking;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.ActivityRepository;
import com.example.habittracker.repository.HabitTrackingRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitTrackingService {

    @Autowired
    private HabitTrackingRepository habitTrackingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Marks a habit as completed and updates the user's streak.
     */
    public String markHabitCompletion(String username, Long activityId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Activity> activityOptional = activityRepository.findById(activityId);

        if (userOptional.isEmpty() || activityOptional.isEmpty()) {
            return "Error: User or activity not found!";
        }

        User user = userOptional.get();
        Activity activity = activityOptional.get();
        LocalDate today = LocalDate.now();

        // ✅ Check if the habit was already completed today
        Optional<HabitTracking> todayEntry = habitTrackingRepository.findByUserAndActivityAndCompletionDate(user, activity, today);
        if (todayEntry.isPresent()) {
            return "Habit already marked as completed today! Streak remains: " + todayEntry.get().getStreakCount();
        }

        // ✅ Fetch last habit entry
        Optional<HabitTracking> lastEntryOpt = habitTrackingRepository.findTopByUserAndActivityOrderByCompletionDateDesc(user, activity);
        int streakCount = 1; // Default streak count

        if (lastEntryOpt.isPresent()) {
            HabitTracking lastEntry = lastEntryOpt.get();
            LocalDate lastCompletedDate = lastEntry.getCompletionDate();

            // ✅ If last completed date was **yesterday**, increase streak
            if (lastCompletedDate.plusDays(1).isEqual(today)) {
                streakCount = lastEntry.getStreakCount() + 1;
            }
        }

        // ✅ Save new habit entry
        HabitTracking habitTracking = new HabitTracking();
        habitTracking.setUser(user);
        habitTracking.setActivity(activity);
        habitTracking.setCompletionDate(today);
        habitTracking.setCompleted(true);
        habitTracking.setStreakCount(streakCount);

        habitTrackingRepository.save(habitTracking);
        return "Habit marked as completed! Current streak: " + streakCount;
    }



    /**
     * Gets the user's current streak.
     */
    public int getUserStreak(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<HabitTracking> habits = habitTrackingRepository.findByUserOrderByCompletionDateDesc(user);

            if (!habits.isEmpty()) {
                return habits.get(0).getStreakCount();
            }
        }
        return 0;
    }

    /**
     * Fetches a user's habit history.
     */
    public List<HabitTracking> getUserHabitHistory(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(habitTrackingRepository::findByUserOrderByCompletionDateDesc).orElse(null);
    }

    /**
     * Fetches habits completed today or within the last 'days' days.
     */
    public List<HabitTracking> getHabitsForToday(String username, int days) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            LocalDate startDate = LocalDate.now().minusDays(days);
            return habitTrackingRepository.findByUserAndCompletionDateAfterOrderByCompletionDateDesc(user, startDate);
        }
        return null;
    }
}
