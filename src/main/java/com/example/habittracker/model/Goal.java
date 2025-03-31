package com.example.habittracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalName;
    private String description;
    private LocalDate startDate;
    private LocalDate targetDate;

    @ElementCollection
    private List<Integer> milestones;

    private int progress;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ✅ Constructors
    public Goal() {}

    public Goal(User user, String goalName, String description, LocalDate startDate, LocalDate targetDate, List<Integer> milestones) {
        this.user = user;
        this.goalName = goalName;
        this.description = description;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.milestones = milestones;
        this.progress = 0;
        this.completed = false;
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGoalName() { return goalName; }
    public void setGoalName(String goalName) { this.goalName = goalName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public List<Integer> getMilestones() { return milestones; }
    public void setMilestones(List<Integer> milestones) { this.milestones = milestones; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
