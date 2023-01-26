package com.api.votingsession.domain.model;

import com.api.votingsession.domain.Enum.AgendaTopic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "TB_AGENDA", uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class Agenda implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgendaTopic topic;

    @Column(nullable = false, unique = true, length = 110)
    private String title;

    @Column(nullable = false, unique = true, length = 999)
    private String description;

    @OneToMany
    @Column
    private List<Vote> votes = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private LocalDateTime votingClosedDate;

    public Agenda() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AgendaTopic getTopic() {
        return topic;
    }

    public void setTopic(AgendaTopic topic) {
        this.topic = topic;
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

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getVotingClosedDate() {
        return votingClosedDate;
    }

    public void setVotingClosedDate(LocalDateTime votingClosedDate) {
        this.votingClosedDate = votingClosedDate;
    }
}
