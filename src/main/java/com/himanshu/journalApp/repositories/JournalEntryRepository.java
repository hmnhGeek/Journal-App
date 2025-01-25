package com.himanshu.journalApp.repositories;

import com.himanshu.journalApp.entities.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

}
