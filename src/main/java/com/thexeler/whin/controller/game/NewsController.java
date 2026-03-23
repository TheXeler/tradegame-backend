package com.thexeler.whin.controller.game;

import com.thexeler.whin.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game/news")
@CrossOrigin
public class NewsController {

    @Autowired
    private NewsRepository repository;

    @GetMapping("/getList")
    public Map<String, Object> getList() {
        return Map.of("message", "Success", "data", repository.findAll());
    }

    @GetMapping("/getInfo")
    public Map<String, Object> getInfo(@RequestParam Long id) {
        return repository.findById(id)
                .map(news -> Map.of("message", "Success", "data", news))
                .orElseGet(() -> Map.of("message", "News not found"));
    }
}
