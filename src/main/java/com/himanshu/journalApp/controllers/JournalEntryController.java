package com.himanshu.journalApp.controllers;

import com.himanshu.journalApp.entities.JournalEntry;
import com.himanshu.journalApp.services.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    /**
     *
     * @return  {@code List<JournalEntry>} A list of all the saved journal entries in the MongoDB collection.
     * This is a {@code GET} endpoint - {@code /journal}.
     */
    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    /**
     *
     * @param journalEntry A request body of type {@code JournalEntry}.
     * @return A saved {@code JournalEntry} in the MongoDB collection.
     *
     * This is a {@code POST} endpoint - {@code /journal}.
     */
    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry) {
        JournalEntry savedJournalEntry = journalEntryService.save(journalEntry);
        return savedJournalEntry;
    }

    @GetMapping("{id}")
    public JournalEntry getJournalEntryById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("{id}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("{id}")
    public JournalEntry updateJournalEntryById(@PathVariable Long id, @RequestBody JournalEntry journalEntry) {
        return null;
    }
}
