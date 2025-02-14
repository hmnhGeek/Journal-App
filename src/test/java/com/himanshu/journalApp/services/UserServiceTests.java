package com.himanshu.journalApp.services;

import com.himanshu.journalApp.repositories.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest is used to tell the JVM that for testing we need to start a kind of
// Spring Boot application with component scans, etc.
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled // we can disable the test also.
    @Test
    public void testFindByUserName() {
        // This user must be present in the database collection for the test to pass.
        assertNotNull(userRepository.findByUserName("ram"));
    }

    // we can use parameterized testing also
    @ParameterizedTest
    // either pass a CSV file or use this annotation to manually pass the arguments.
    @CsvSource({
            "1,1,2",
            "2,2,8",
            "2,3,5"
    })
    public void testSum(int a, int b, int c) {
        assertEquals(c, a + b);
    }
}
