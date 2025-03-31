package com.example.habittracker.controller;

import com.example.habittracker.model.Activity;
import com.example.habittracker.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // ✅ Add Activity
    @PostMapping("/add/{username}")
    public ResponseEntity<?> addActivity(@PathVariable String username, @RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.addActivity(username, activity));
    }

    // ✅ Get Activities by User
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getActivitiesByUser(@PathVariable String username) {
        List<Map<String, Object>> activities = activityService.getActivitiesByUser(username);
        return ResponseEntity.ok(activities);
    }

    // ✅ Update Activity
    @PutMapping("/update/{activityId}")
    public ResponseEntity<?> updateActivity(@PathVariable Long activityId, @RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.updateActivity(activityId, activity));
    }

    // ✅ Delete Activity
    @DeleteMapping("/delete/{activityId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long activityId) {
        return ResponseEntity.ok(activityService.deleteActivity(activityId));
    }
}
