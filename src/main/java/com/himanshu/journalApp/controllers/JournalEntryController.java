package com.himanshu.journalApp.controllers;

import com.himanshu.journalApp.entities.JournalEntry;
import com.himanshu.journalApp.entities.User;
import com.himanshu.journalApp.services.JournalEntryService;
import com.himanshu.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    /**
     * @return  {@code List<JournalEntry>} A list of all the saved journal entries in the MongoDB collection for a particular user.
     * This is a {@code GET} endpoint - {@code /journal}.
     */
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
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
     * This is a {@code POST} endpoint - {@code /journal}.
     */
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> list = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb
     * @param userName A string type denoting the user whose journal entry is being deleted.
     * @return A {@code NO_CONTENT} HTTP Status.
     */
    @DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id, @PathVariable String userName) {
        journalEntryService.deleteById(id, userName);
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
