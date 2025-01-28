package com.himanshu.journalApp.controllers;

import com.himanshu.journalApp.entities.User;
import com.himanshu.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User savedUser = userService.findByUserName(userName);
        if (savedUser != null) {
            savedUser.setUserName(user.getUserName());
            savedUser.setPassword(user.getPassword());
            userService.save(savedUser);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
