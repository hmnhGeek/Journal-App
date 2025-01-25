package com.himanshu.journalApp.services;

import com.himanshu.journalApp.entities.JournalEntry;
import com.himanshu.journalApp.repositories.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * It is important to add {@code @Component} annotation to this class so that a bean can be created for this class at
 * runtime; wherever it is injected.
 */
@Component
public class JournalEntryService {

    /**
     * This is an interface but Spring Boot will create an implementation of this interface at runtime because of the
     * {@code @Autowired} annotation.
     */
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    /**
     *
     * @param journalEntry Parameter of type {@code JournalEntry}.
     * @return {@code JournalEntry} by saving the entry inside the MongoDB collection.
     */
    public JournalEntry save(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
        return savedJournalEntry;
    }

    /**
     * This method returns all the saved journal entries from the collection.
     * @return The return type is {@code List<JournalEntry>}.
     */
    public List<JournalEntry> getAll() {
        List<JournalEntry> allSavedJournalEntries = journalEntryRepository.findAll();
        return allSavedJournalEntries;
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb collection.
     * @return Optional journal entry.
     */
    public Optional<JournalEntry> getById(ObjectId id) {
        // Optional is like a box; there can be data inside it or not.
        Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
        return journalEntry;
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb
     */
    public void deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}
