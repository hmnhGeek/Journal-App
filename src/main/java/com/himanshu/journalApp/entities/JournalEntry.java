package com.himanshu.journalApp.entities;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/*
* This is a Plain Old Java Object (POJO).
*
* The @Document annotation specifies that the instance of a JournalEntry class will map to a document in the MongoDB
* collection.
* */
@Document(collection = "journal_entries")
@Data
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}
