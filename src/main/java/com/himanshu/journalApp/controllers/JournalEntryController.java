package com.himanshu.journalApp.controllers;

import com.himanshu.journalApp.entities.JournalEntry;
import com.himanshu.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    /**
     *
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb collection.
     * @return A {@code JournalEntry} or else if not present, then returns a bad request.
     */
    @GetMapping("{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb
     */
    @DeleteMapping("{id}")
    public void deleteJournalEntryById(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} from the mongodb collection.
     * @param journalEntry A parameter of type {@code JournalEntry} with updated value.
     * @return The updated journal entry.
     */
    @PutMapping("{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry) {
        JournalEntry updatedJournalEntry = journalEntryService.updateById(id, journalEntry);
        return updatedJournalEntry;
    }
}
