package com.himanshu.journalApp.services;

import com.himanshu.journalApp.entities.JournalEntry;
import com.himanshu.journalApp.entities.User;
import com.himanshu.journalApp.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     *
     * @param user Parameter of type {@code User}.
     * @return {@code User} by saving the entry inside the MongoDB collection.
     */
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedJournalEntry = userRepository.save(user);
        return user;
    }

    /**
     * This method returns all the saved users from the collection.
     * @return The return type is {@code List<User>}.
     */
    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb collection.
     * @return Optional user.
     */
    public Optional<User> getById(ObjectId id) {
        // Optional is like a box; there can be data inside it or not.
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    /**
     *
     * @param id A parameter of type {@code ObjectId} corresponding to the primary key in the mongodb
     */
    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    /**
     *
     * @param userName A string value denoting the case-sensitive username.
     * @return A {@code User} data type extracted from the database.
     */
    public User findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return user;
    }
}
