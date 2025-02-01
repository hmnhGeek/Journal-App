package com.himanshu.journalApp.services;

import com.himanshu.journalApp.entities.JournalEntry;
import com.himanshu.journalApp.entities.User;
import com.himanshu.journalApp.repositories.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    private UserService userService;

    /**
     *
     * @param journalEntry Parameter of type {@code JournalEntry}.
     * @return {@code JournalEntry} by saving the entry inside the MongoDB collection.
     */
    @Transactional
    public JournalEntry saveJournalEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
        userService.addJournalEntryInUser(user, savedJournalEntry);
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
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb
     * @param userName A string parameter denoting the user whose journal entry is being deleted.
     */
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> list = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
        if (list.isEmpty()) {
            return false;
        }
        journalEntryRepository.deleteById(id);
        userService.removeJournalEntryInUser(user, id);
        return true;
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} from the mongodb collection.
     * @param journalEntry A parameter of type {@code JournalEntry} with updated value.
     * @return The updated journal entry.
     */
    public JournalEntry updateById(ObjectId id, JournalEntry journalEntry) {
        JournalEntry oldJournalEntry = journalEntryRepository.findById(id).orElse(null);
        if (oldJournalEntry != null) {
            oldJournalEntry.setTitle(journalEntry.getTitle() != null && !Objects.equals(journalEntry.getTitle(), "") ? journalEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(journalEntry.getContent() != null && !Objects.equals(journalEntry.getContent(), "") ? journalEntry.getContent() : oldJournalEntry.getContent());
        }
        journalEntryRepository.save(oldJournalEntry);
        return oldJournalEntry;
    }
}
