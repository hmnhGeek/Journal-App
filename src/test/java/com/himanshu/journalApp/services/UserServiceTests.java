package com.himanshu.journalApp.services;

import com.himanshu.journalApp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest is used to tell the JVM that for testing we need to start a kind of
// Spring Boot application with component scans, etc.
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName() {
        // This user must be present in the database collection for the test to pass.
        assertNotNull(userRepository.findByUserName("ram"));
    }
}
