package com.thexeler.trade.controller.game;

import com.thexeler.trade.entity.News;
import com.thexeler.trade.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game/news")
@CrossOrigin
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/getList")
    public Map<String, Object> getList() {
        return Map.of("message", "Success", "data", newsRepository.findAll());
    }

    @GetMapping("/getInfo")
    public Map<String, Object> getInfo(@RequestParam Long id) {
        return newsRepository.findById(id)
                .map(news -> Map.of("message", "Success", "data", news))
                .orElseGet(() -> Map.of("message", "News not found"));
    }
}
