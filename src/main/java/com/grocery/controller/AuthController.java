package com.grocery.controller;

import com.grocery.model.User;
import com.grocery.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userRepository.save(user);
        return "REGISTER_SUCCESS";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> found =
                userRepository.findByEmailAndPassword(
                        user.getEmail(),
                        user.getPassword()
                );

        return found.isPresent() ? "LOGIN_SUCCESS" : "LOGIN_FAILED";
    }
}
