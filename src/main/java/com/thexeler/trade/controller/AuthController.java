package com.thexeler.trade.controller;

import com.thexeler.trade.dto.auth.AllSecurityRequest;
import com.thexeler.trade.dto.auth.PasswordRequest;
import com.thexeler.trade.dto.auth.TokenRequest;
import com.thexeler.trade.entity.User;
import com.thexeler.trade.kits.TokenData;
import com.thexeler.trade.kits.TokenKits;
import com.thexeler.trade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody PasswordRequest passwordRequest) {
        Optional<User> userOptional = userRepository.findByUsername(passwordRequest.getUsername());

        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username or password");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(passwordRequest.getPassword(), user.getPassword())) {
            return Map.of("message", "Invalid username or password");
        }

        return Map.of("message", "Success", "token", TokenKits.generateToken(userRepository, user));
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody PasswordRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return Map.of("message", "Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setAdmin(true);
        newUser.setBalance(0);

        userRepository.save(newUser);

        return Map.of("message", "Success", "token", TokenKits.generateToken(userRepository, newUser));
    }

    @PostMapping("/changePassword")
    public Map<String, Object> changePassword(@RequestBody AllSecurityRequest allSecurityRequest) {
        Optional<User> userOptional = userRepository.findByUsername(allSecurityRequest.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        User user = userOptional.get();
        if (!allSecurityRequest.getToken().equals(user.getLastToken())) {
            return Map.of("message", "Invalid token");
        }

        user.setPassword(passwordEncoder.encode(allSecurityRequest.getPassword()));
        userRepository.save(user);

        return Map.of("message", "Success");
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody TokenRequest tokenRequest) {
        Optional<User> userOptional = userRepository.findByUsername(tokenRequest.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        User user = userOptional.get();
        if (!tokenRequest.getToken().equals(user.getLastToken())) {
            return Map.of("message", "Invalid token");
        }

        return Map.of("message", "Success", "token", TokenKits.generateToken(userRepository, user));
    }

    @PostMapping("/verify")
    public Map<String, Object> verify(@RequestBody TokenRequest tokenRequest) {
        Optional<User> userOptional = userRepository.findByUsername(tokenRequest.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        User user = userOptional.get();
        boolean isValid = tokenRequest.getToken().equals(user.getLastToken()) &&
                TokenKits.verifyToken(new TokenData(tokenRequest.getToken()), tokenRequest.getUsername());

        return Map.of("message", "Success", "isValid", isValid);
    }
}
