package com.example.habittracker.repository;

import com.example.habittracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);

    // ✅ Add this method to check if a user exists by username
    boolean existsByUsername(String username);
}
