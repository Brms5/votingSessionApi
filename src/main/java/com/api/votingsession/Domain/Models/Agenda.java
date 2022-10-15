package com.api.votingsession.Domain.Models;

import com.api.votingsession.Domain.Enums.AgendaTopic;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_AGENDA")
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private Integer topic;
    @Column(nullable = false, unique = true, length = 110)
    private String title;
    @Column(nullable = false, unique = true, length = 999)
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AgendaTopic getTopic() {
        return AgendaTopic.valueOf(topic);
    }

    public void setTopic(AgendaTopic topic) {
        this.topic = topic.getCode();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
