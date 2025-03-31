package com.example.habittracker.service;

import com.example.habittracker.model.Activity;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.ActivityRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {

    private final ActivityRepository actRepo;
    private final UserRepository usrRepo;

    public ActivityService(ActivityRepository actRepo, UserRepository usrRepo) {
        this.actRepo = actRepo;
        this.usrRepo = usrRepo;
    }

    // ✅ Add Activity
    public Map<String, Object> createActivity(String usrName, Activity act) {
        User foundUser = usrRepo.findByUsername(usrName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        act.setUser(foundUser); // ✅ Assign user to activity
        Activity savedAct = actRepo.save(act);

        return formatActivityData(savedAct);
    }

    // ✅ Get Activities by User
    public List<Map<String, Object>> fetchActivitiesByUser(String usrName) {
        User foundUser = usrRepo.findByUsername(usrName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Activity> actList = actRepo.findByUser(foundUser);

        return actList.stream().map(this::formatActivityData).toList();
    }

    // ✅ Update Activity
    public Map<String, Object> modifyActivity(Long actId, Activity newAct) {
        Activity existingAct = actRepo.findById(actId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        existingAct.setActivityName(newAct.getActivityName());
        existingAct.setActivityType(newAct.getActivityType());
        existingAct.setStartTime(newAct.getStartTime());
        existingAct.setEndTime(newAct.getEndTime());

        Activity savedAct = actRepo.save(existingAct);
        return formatActivityData(savedAct);
    }

    // ✅ Delete Activity
    public String removeActivity(Long actId) {
        Activity actToDelete = actRepo.findById(actId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        actRepo.delete(actToDelete);
        return "Activity deleted successfully!";
    }

    // ✅ Format Response
    private Map<String, Object> formatActivityData(Activity act) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", act.getId());
        response.put("activityName", act.getActivityName());
        response.put("activityType", act.getActivityType());
        response.put("startTime", act.getStartTime());
        response.put("endTime", act.getEndTime());

        Map<String, String> userData = new HashMap<>();
        userData.put("id", act.getUser().getId().toString());
        userData.put("username", act.getUser().getUsername());

        response.put("user", userData);
        return response;
    }
}
