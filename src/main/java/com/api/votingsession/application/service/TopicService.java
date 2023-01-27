package com.api.votingsession.application.service;

import com.api.votingsession.Repository.TopicRepository;
import com.api.votingsession.Utility.CustomException.MessageBusiness;
import com.api.votingsession.application.Interface.ITopicService;
import com.api.votingsession.domain.Entity.Topic;
import com.api.votingsession.domain.Enum.AgendaTopic;
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
    public Topic createNewTopic(String topic) {
        List<Topic> topicList = topicRepository.findAll();
        for ( Topic topic1 : topicList ) {
            if (topic1.getTopic().equals(topic))
                throw MessageBusiness.TOPIC_INVALID.createException();
        }

        Topic newTopic = new Topic();
        for ( AgendaTopic enumTopic : AgendaTopic.values() ) {
            if (topic.equals(enumTopic.toString())) {
                newTopic.setTopic(topic);
                topicRepository.save(newTopic);
            }
        }

        return newTopic;
    }
}
