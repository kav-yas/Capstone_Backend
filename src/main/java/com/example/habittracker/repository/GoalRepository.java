package com.example.habittracker.repository;

import com.example.habittracker.model.Goal;
import com.example.habittracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    // ✅ Get all goals for a user
    List<Goal> findByUser(User user);

    // ✅ Get all active (not completed) goals for a user
    List<Goal> findByUserAndCompletedFalse(User user);

    // ✅ Get a specific goal by ID
    Optional<Goal> findById(Long goalId);

    // ✅ Get completed goals for a user
    List<Goal> findByUserAndCompletedTrue(User user);
}
