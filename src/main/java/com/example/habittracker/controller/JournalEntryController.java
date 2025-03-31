package com.example.habittracker.controller;

import com.example.habittracker.model.JournalEntry;
import com.example.habittracker.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/journal")
@CrossOrigin
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    // ✅ Create a Journal Entry
    @PostMapping("/create/{username}")
    public ResponseEntity<String> createJournalEntry(@PathVariable String username, @RequestBody String content) {
        return ResponseEntity.ok(journalEntryService.createJournalEntry(username, content));
    }

    // ✅ Get All Journal Entries for a User
    @GetMapping("/all/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries(@PathVariable String username) {
        return ResponseEntity.ok(journalEntryService.getAllJournalEntries(username));
    }

    // ✅ Get Journal Entries by Date
    @GetMapping("/date/{username}/{date}")
    public ResponseEntity<List<JournalEntry>> getJournalEntriesByDate(@PathVariable String username, @PathVariable String date) {
        LocalDate entryDate = LocalDate.parse(date);
        return ResponseEntity.ok(journalEntryService.getJournalEntriesByDate(username, entryDate));
    }

    // ✅ Update a Journal Entry
    @PutMapping("/update/{username}/{entryId}")
    public ResponseEntity<String> updateJournalEntry(@PathVariable String username, @PathVariable Long entryId, @RequestBody String newContent) {
        return ResponseEntity.ok(journalEntryService.updateJournalEntry(username, entryId, newContent));
    }

    // ✅ Delete a Journal Entry
    @DeleteMapping("/delete/{username}/{entryId}")
    public ResponseEntity<String> deleteJournalEntry(@PathVariable String username, @PathVariable Long entryId) {
        return ResponseEntity.ok(journalEntryService.deleteJournalEntry(username, entryId));
    }
}
