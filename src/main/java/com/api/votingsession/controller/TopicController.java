package com.api.votingsession.controller;

import com.api.votingsession.Repository.TopicRepository;
import com.api.votingsession.application.service.TopicService;
import com.api.votingsession.domain.Entity.Topic;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/topic")
public class TopicController {

    final TopicService topicService;

    final TopicRepository topicRepository;

    public TopicController(TopicService topicService, TopicRepository topicRepository) {
        this.topicService = topicService;
        this.topicRepository = topicRepository;
    }

    @GetMapping
    @ApiOperation(value = "Request all topics", notes = "Search for all topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        return ResponseEntity.status(HttpStatus.OK).body(topicRepository.findAll());
    }

    @PostMapping
    @ApiOperation(value = "Create new topic", notes = "To create a new topic, it must be valid within the system", hidden = true)
    public ResponseEntity<Topic> createNewTopic(String topic) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.createNewTopic(topic));
    }
}
