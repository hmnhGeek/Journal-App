package com.himanshu.journalApp.services;

import com.himanshu.journalApp.entities.User;
import com.himanshu.journalApp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.*;

// Use this annotation to initialize Spring context for testing.
@SpringBootTest
public class UserDetailsServiceImplTests {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // When using spring context for testing, use @MockitoBean to avoid DB connection.
    // Why for user repository? Because it is used inside the details service.
    @MockitoBean
    private UserRepository userRepository;

    @Test
    void loadUserByUsernameTest() {
        // whenever userRepository is called, return a dummy user.
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn((com.himanshu.journalApp.entities.User) User.builder().userName("ram").password("skhfsdfhkd").roles(Collections.singletonList(String.valueOf(new ArrayList<>()))).build());
        UserDetails userDetails = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(userDetails);
    }

}
