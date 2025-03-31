package com.example.habittracker.service;

import com.example.habittracker.model.JournalEntry;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.JournalEntryRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Create a new journal entry
    public String createJournalEntry(String username, String content) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setUser(user);
            journalEntry.setEntryDate(LocalDate.now());
            journalEntry.setContent(content);

            journalEntryRepository.save(journalEntry);
            return "Journal entry created successfully!";
        }
        return "User not found!";
    }

    // ✅ Get all journal entries for a user
    public List<JournalEntry> getAllJournalEntries(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(journalEntryRepository::findByUser).orElse(null);
    }

    // ✅ Get journal entries by date for a user
    public List<JournalEntry> getJournalEntriesByDate(String username, LocalDate date) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(user -> journalEntryRepository.findByUserAndEntryDate(user, date)).orElse(null);
    }

    // ✅ Update a journal entry
    public String updateJournalEntry(String username, Long entryId, String newContent) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            Optional<JournalEntry> entryOptional = journalEntryRepository.findByIdAndUser(entryId, userOptional.get());
            if (entryOptional.isPresent()) {
                JournalEntry entry = entryOptional.get();
                entry.setContent(newContent);
                journalEntryRepository.save(entry);
                return "Journal entry updated successfully!";
            }
            return "Journal entry not found!";
        }
        return "User not found!";
    }

    // ✅ Delete a journal entry
    public String deleteJournalEntry(String username, Long entryId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            Optional<JournalEntry> entryOptional = journalEntryRepository.findByIdAndUser(entryId, userOptional.get());
            if (entryOptional.isPresent()) {
                journalEntryRepository.delete(entryOptional.get());
                return "Journal entry deleted successfully!";
            }
            return "Journal entry not found!";
        }
        return "User not found!";
    }
}
