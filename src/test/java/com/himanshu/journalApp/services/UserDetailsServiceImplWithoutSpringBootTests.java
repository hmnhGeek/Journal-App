package com.himanshu.journalApp.services;

import com.himanshu.journalApp.entities.User;
import com.himanshu.journalApp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplWithoutSpringBootTests {

    // Since there is no @SpringBootTests on the class now, we would want to inject mock dependencies of this service
    // class.
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    // we would now simply use @Mock instead of @MockitoBean as bean is not required now.
    @Mock
    private UserRepository userRepository;

    // initialize all the mocks before each test.
    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        // whenever userRepository is called, return a dummy user.
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn((com.himanshu.journalApp.entities.User) User.builder().userName("ram").password("skhfsdfhkd").roles(Collections.singletonList(String.valueOf(new ArrayList<>()))).build());
        UserDetails userDetails = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(userDetails);
    }

}
