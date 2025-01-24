package com.himanshu.journalApp.controllers;

import com.himanshu.journalApp.entities.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll() {
        // This will map to /journal GET.
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        // This will map to /journal POST.
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("{id}")
    public JournalEntry getJournalEntryById(@PathVariable Long id) {
        JournalEntry journalEntry = journalEntries.get(id);
        return journalEntry;
    }

    @DeleteMapping("{id}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long id) {
        JournalEntry removedEntry = journalEntries.remove(id);
        return removedEntry;
    }

    @PutMapping("{id}")
    public JournalEntry updateJournalEntryById(@PathVariable Long id, @RequestBody JournalEntry journalEntry) {
        JournalEntry updatedEntry = journalEntries.put(id, journalEntry);
        return updatedEntry;
    }
}
