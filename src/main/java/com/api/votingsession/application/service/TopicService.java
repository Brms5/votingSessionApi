package com.api.votingsession.application.service;

import com.api.votingsession.Repository.TopicRepository;
import com.api.votingsession.Utility.CustomException.MessageBusiness;
import com.api.votingsession.application.Interface.ITopicService;
import com.api.votingsession.domain.Entity.Topic;
import com.api.votingsession.domain.Enum.AgendaTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TopicService implements ITopicService {

    final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Transactional
    public ResponseEntity<Object> createNewTopic(String topic) {
        List<Topic> topicList = topicRepository.findAll();
        for ( Topic topic1 : topicList ) {
            if (topic1.getTopic().equals(topic))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageBusiness.TOPIC_INVALID.createException(topic).getMessage());
        }

        for ( AgendaTopic enumTopic : AgendaTopic.values() ) {
            if (topic.equals(enumTopic.toString())) {
                Topic newTopic = new Topic();
                newTopic.setTopic(topic);
                return ResponseEntity.status(HttpStatus.CREATED).body(topicRepository.save(newTopic));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageBusiness.TOPIC_INVALID.createException(topic).getMessage());
    }
}
