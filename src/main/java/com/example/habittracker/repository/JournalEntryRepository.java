package com.example.habittracker.repository;

import com.example.habittracker.model.JournalEntry;
import com.example.habittracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    // ✅ Fetch all journal entries for a specific user
    List<JournalEntry> findByUser(User user);

    // ✅ Fetch journal entries by user and date
    List<JournalEntry> findByUserAndEntryDate(User user, LocalDate entryDate);

    // ✅ Find a specific journal entry by ID and User (to ensure only the owner can modify/delete)
    Optional<JournalEntry> findByIdAndUser(Long id, User user);
}
