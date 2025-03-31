package com.example.habittracker.service;

import com.example.habittracker.model.Goal;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Create a goal for a user
    public Goal createGoal(String username, String goalName, String description, LocalDate startDate, LocalDate targetDate, List<Integer> milestones) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Goal goal = new Goal(user, goalName, description, startDate, targetDate, milestones);
        return goalRepository.save(goal);
    }

    // ✅ Get all goals for a user
    public List<Goal> getUserGoals(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        return goalRepository.findByUser(user);
    }

    // ✅ Get a specific goal by ID
    public Goal getGoalById(Long goalId) {
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found!"));
    }

    // ✅ Update goal progress
    public Goal updateGoalProgress(Long goalId, int progress) {
        Goal goal = getGoalById(goalId);

        // 🛠 Ensure the Goal class has a setProgress() method
        goal.setProgress(progress);

        if (progress >= 100) {
            goal.setCompleted(true);
        }

        return goalRepository.save(goal);
    }

    // ✅ Mark goal as completed
    public Goal completeGoal(Long goalId) {
        Goal goal = getGoalById(goalId);
        goal.setCompleted(true);
        goal.setProgress(100);
        return goalRepository.save(goal);
    }

    // ✅ Delete a goal (with validation)
    public void deleteGoal(Long goalId) {
        if (!goalRepository.existsById(goalId)) {
            throw new IllegalArgumentException("Goal not found!");
        }
        goalRepository.deleteById(goalId);
    }
}
