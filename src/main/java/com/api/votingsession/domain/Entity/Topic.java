package com.api.votingsession.domain.Entity;

import com.api.votingsession.domain.Enum.AgendaTopic;

import javax.persistence.*;

@Entity
@Table(name = "TB_TOPIC", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String topic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
