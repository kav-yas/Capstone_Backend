package com.example.habittracker.dto;

import com.example.habittracker.model.Activity;
import java.time.LocalDate;

public class HabitTrackingDTO {
    private Long id;
    private String activityName;
    private String activityType;
    private LocalDate completionDate;
    private boolean completed;

    // Constructor
    public HabitTrackingDTO(Long id, String activityName, String activityType, LocalDate completionDate, boolean completed) {
        this.id = id;
        this.activityName = activityName;
        this.activityType = activityType;
        this.completionDate = completionDate;
        this.completed = completed;
    }

    // Getters
    public Long getId() { return id; }
    public String getActivityName() { return activityName; }
    public String getActivityType() { return activityType; }
    public LocalDate getCompletionDate() { return completionDate; }
    public boolean isCompleted() { return completed; }
}
