package com.api.votingsession.Repository;

import com.api.votingsession.domain.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {}
