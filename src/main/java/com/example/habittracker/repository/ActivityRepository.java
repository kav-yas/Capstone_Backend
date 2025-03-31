package com.example.habittracker.repository;

import com.example.habittracker.model.Activity;
import com.example.habittracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUser(User user);
}
