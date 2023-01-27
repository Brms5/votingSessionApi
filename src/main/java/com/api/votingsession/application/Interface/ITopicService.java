package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.Entity.Topic;
import org.springframework.http.ResponseEntity;

public interface ITopicService {
    Topic createNewTopic(String topic);
}
