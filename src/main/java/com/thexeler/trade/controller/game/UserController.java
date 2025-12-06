package com.thexeler.trade.controller.game;

import com.thexeler.trade.dto.game.user.AllQueryRequest;
import com.thexeler.trade.dto.game.user.TypeUpdateRequest;
import com.thexeler.trade.dto.game.user.TypeQueryRequest;
import com.thexeler.trade.entity.User;
import com.thexeler.trade.kits.TokenData;
import com.thexeler.trade.kits.TokenKits;
import com.thexeler.trade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/game/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/queryAllGameValue")
    public Map<String, Object> queryAllGameValue(@RequestBody AllQueryRequest allQueryRequest) {
        TokenData data = new TokenData(allQueryRequest.getToken());
        if (TokenKits.verifyToken(data)) {
            return Map.of("message", "Invalid token");
        }
        if (allQueryRequest.getUsername() == null || allQueryRequest.getUsername().isEmpty()) {
            allQueryRequest.setUsername(data.getUsername());
        }

        Optional<User> userOptional = userRepository.findByUsername(data.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        User currentUser = userOptional.get();
        if (currentUser.getUsername().equals(allQueryRequest.getUsername())) {
            return Map.of("message", "Success", "data", currentUser.getAllGameValue());
        }
        if (!currentUser.isAdmin()) {
            return Map.of("message", "Access denied");
        }

        return userRepository.findByUsername(allQueryRequest.getUsername()).map(user -> Map.of("message", "Success", "data", user.getAllGameValue())).orElseGet(() -> Map.of("message", "Invalid username"));
    }

    @PostMapping("/queryGameValue")
    public Map<String, Object> queryGameValue(@RequestBody TypeQueryRequest typeQueryRequest) {
        TokenData data = new TokenData(typeQueryRequest.getToken());
        if (TokenKits.verifyToken(data)) {
            return Map.of("message", "Invalid token");
        }
        if (typeQueryRequest.getUsername() == null || typeQueryRequest.getUsername().isEmpty()) {
            typeQueryRequest.setUsername(data.getUsername());
        }

        Optional<User> userOptional = userRepository.findByUsername(data.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        User currentUser = userOptional.get();
        if (currentUser.getUsername().equals(typeQueryRequest.getUsername())) {
            return Map.of("message", "Success", "data", Map.of("value", currentUser.getGameValue(typeQueryRequest.getType())));
        }
        if (!currentUser.isAdmin()) {
            return Map.of("message", "Access denied");
        }

        Optional<User> targetUserOptional = userRepository.findByUsername(typeQueryRequest.getUsername());

        return targetUserOptional.map(user -> Map.of("message", "Success", "data", Map.of("value", user.getGameValue(typeQueryRequest.getType())))).orElseGet(() -> Map.of("message", "Invalid username"));
    }


    @PostMapping("/updateGameValue")
    public Map<String, Object> updateGameValue(@RequestBody TypeUpdateRequest typeUpdateRequest) {
        TokenData data = new TokenData(typeUpdateRequest.getToken());
        if (TokenKits.verifyToken(data)) {
            return Map.of("message", "Invalid token");
        }
        if (typeUpdateRequest.getUsername() == null || typeUpdateRequest.getUsername().isEmpty()) {
            typeUpdateRequest.setUsername(data.getUsername());
        }

        Optional<User> userOptional = userRepository.findByUsername(data.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        User currentUser = userOptional.get();
        if (currentUser.getUsername().equals(typeUpdateRequest.getUsername())) {
            currentUser.setGameValue(typeUpdateRequest.getType(), Long.parseLong(typeUpdateRequest.getValue()));
            userRepository.save(currentUser);
            return Map.of("message", "Success");
        }
        if (!currentUser.isAdmin()) {
            return Map.of("message", "Access denied");
        }

        Optional<User> targetUserOptional = userRepository.findByUsername(typeUpdateRequest.getUsername());
        if (targetUserOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        User targetUser = targetUserOptional.get();
        targetUser.setGameValue(typeUpdateRequest.getType(), Long.parseLong(typeUpdateRequest.getValue()));
        userRepository.save(targetUser);

        return Map.of("message", "Success");
    }
}
