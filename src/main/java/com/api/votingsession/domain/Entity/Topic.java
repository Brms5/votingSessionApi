package com.api.votingsession.domain.Entity;

import com.api.votingsession.domain.Enum.AgendaTopic;

import javax.persistence.*;

@Entity
@Table(name = "TB_TOPIC", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "AgendaTopic")
    private AgendaTopic topic;

    public AgendaTopic getTopic() {
        return topic;
    }

    public void setTopic(AgendaTopic topic) {
        this.topic = topic;
    }
}
