package com.api.votingsession.application.Interface;

import org.springframework.http.ResponseEntity;

public interface ITopicService {
    ResponseEntity<Object> createNewTopic(String topic);
}
