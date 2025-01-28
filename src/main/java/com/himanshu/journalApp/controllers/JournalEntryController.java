package com.himanshu.journalApp.controllers;

import com.himanshu.journalApp.entities.JournalEntry;
import com.himanshu.journalApp.entities.User;
import com.himanshu.journalApp.services.JournalEntryService;
import com.himanshu.journalApp.services.UserService;
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

    @Autowired
    private UserService userService;

    /**
     * @param userName A string value representing the username stored in the {@code users} collection.
     * @return  {@code List<JournalEntry>} A list of all the saved journal entries in the MongoDB collection for a particular user.
     * This is a {@code GET} endpoint - {@code /journal/<userName>}.
     */
    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> journalEntries = user.getJournalEntries();
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    /**
     *
     * @param journalEntry A request body of type {@code JournalEntry}.
     * @return A saved {@code JournalEntry} in the MongoDB collection.
     *
     * This is a {@code POST} endpoint - {@code /journal}.
     */
    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        JournalEntry savedJournalEntry = journalEntryService.saveJournalEntry(journalEntry, userName);
        return new ResponseEntity<>(savedJournalEntry, HttpStatus.CREATED);
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb collection.
     * @return A {@code JournalEntry} or else if not present, then returns a bad request.
     */
    @GetMapping("/id/{id}")
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
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} from the mongodb collection.
     * @param journalEntry A parameter of type {@code JournalEntry} with updated value.
     * @return The updated journal entry.
     */
    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry) {
        JournalEntry updatedJournalEntry = journalEntryService.updateById(id, journalEntry);
        return new ResponseEntity<>(updatedJournalEntry, HttpStatus.OK);
    }
}
