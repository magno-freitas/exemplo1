package com.freitas.exemplo1.controller;

import com.freitas.exemplo1.model.User;
import com.freitas.exemplo1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUserProfile(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }
}
