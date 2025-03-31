package com.example.habittracker.service;

import com.example.habittracker.model.Activity;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.ActivityRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    // ✅ Add Activity
//    public Map<String, Object> addActivity(String username, Activity activity) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        activity.setUser(user);
//        Activity savedActivity = activityRepository.save(activity);
//
//        return formatActivityResponse(savedActivity);
//    }

    public Map<String, Object> addActivity(String username, Activity activity) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        activity.setUser(user); // ✅ Assign user to activity
        Activity savedActivity = activityRepository.save(activity);

        return formatActivityResponse(savedActivity);
    }


    // ✅ Get Activities by User
    public List<Map<String, Object>> getActivitiesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Activity> activities = activityRepository.findByUser(user);

        return activities.stream().map(this::formatActivityResponse).toList();
    }

    // ✅ Update Activity
    public Map<String, Object> updateActivity(Long activityId, Activity updatedActivity) {
        Activity existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        existingActivity.setActivityName(updatedActivity.getActivityName());
        existingActivity.setActivityType(updatedActivity.getActivityType());
        existingActivity.setStartTime(updatedActivity.getStartTime());
        existingActivity.setEndTime(updatedActivity.getEndTime());

        Activity savedActivity = activityRepository.save(existingActivity);
        return formatActivityResponse(savedActivity);
    }

    // ✅ Delete Activity
    public String deleteActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        activityRepository.delete(activity);
        return "Activity deleted successfully!";
    }

    // ✅ Format Response
    private Map<String, Object> formatActivityResponse(Activity activity) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", activity.getId());
        response.put("activityName", activity.getActivityName());
        response.put("activityType", activity.getActivityType());
        response.put("startTime", activity.getStartTime());
        response.put("endTime", activity.getEndTime());

        Map<String, String> userResponse = new HashMap<>();
        userResponse.put("id", activity.getUser().getId().toString());
        userResponse.put("username", activity.getUser().getUsername());

        response.put("user", userResponse);
        return response;
    }
}
