package com.api.votingsession.controller;

import com.api.votingsession.Repository.TopicRepository;
import com.api.votingsession.domain.Entity.Topic;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.api.votingsession.domain.Enum.AgendaTopic.generateRandomTopic;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class TopicControllerTest {

    @Autowired
    private TopicController topicController;

    @MockBean
    private TopicRepository topicRepository;

    private Topic buildTopic() {
        Random random = new Random();
        Integer id = random.nextInt();
        return Topic.builder().id(id).topic(String.valueOf(generateRandomTopic())).build();
    }

    @Test
    public void getAllTopicsTest() {
        Topic topic = buildTopic();
        List<Topic> topicList = new ArrayList<>();
        topicList.add(topic);
        ResponseEntity<List<Topic>> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(topicList);
        Mockito.when(topicRepository.findAll()).thenReturn(topicList);
        ResponseEntity<List<Topic>> response = topicController.getAllTopics();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }

}
