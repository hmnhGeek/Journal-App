package com.himanshu.journalApp.entities;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId id;

    // This will make search by username easier and will also ensure that usernames are unique.
    // However, this indexing will not be done by default in MongoDB. To enable indexing in MongoDB,
    // either configure the MongoDB instance on your machine or set a property in this project's properties
    // file (spring.data.mongodb.auto-index-creation=true).
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;

    // This will create a reference to the journal_entries collection by just their ObjectId.
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

    // to be used in Spring Security.
    private List<String> roles = new ArrayList<>();
}
