package com.example.habittracker.controller;

import com.example.habittracker.model.Goal;
import com.example.habittracker.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;


    // ✅ Create a goal for a specific user
    @PostMapping("/create/{username}")
    public ResponseEntity<?> createGoal(
            @PathVariable String username,
            @RequestBody Goal goalRequest) {
        try {
            Goal goal = goalService.createGoal(
                    username,
                    goalRequest.getGoalName(),
                    goalRequest.getDescription(),
                    goalRequest.getStartDate(),
                    goalRequest.getTargetDate(),
                    goalRequest.getMilestones()
            );
            return ResponseEntity.ok(goal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Get all goals for a user
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserGoals(@PathVariable String username) {
        try {
            List<Goal> goals = goalService.getUserGoals(username);
            return ResponseEntity.ok(goals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Get Specific Goal by ID
    @GetMapping("/details/{goalId}")
    public ResponseEntity<?> getGoalById(@PathVariable Long goalId) {
        try {
            return ResponseEntity.ok(goalService.getGoalById(goalId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Update Goal Progress (now accepts JSON)
    @PatchMapping("/update/{goalId}")
    public ResponseEntity<?> updateGoalProgress(
            @PathVariable Long goalId,
            @RequestBody Map<String, Integer> request) {
        try {
            int progress = request.get("progress");
            return ResponseEntity.ok(goalService.updateGoalProgress(goalId, progress));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Mark Goal as Completed
    @PatchMapping("/complete/{goalId}")
    public ResponseEntity<?> completeGoal(@PathVariable Long goalId) {
        try {
            return ResponseEntity.ok(goalService.completeGoal(goalId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Delete a Goal
    @DeleteMapping("/delete/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId) {
        try {
            goalService.deleteGoal(goalId);
            return ResponseEntity.ok("Goal deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
