package com.api.votingsession.Domain.Models;

import com.api.votingsession.Domain.Enums.AgendaTopic;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_AGENDA", uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
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
    @OneToMany
    @Column()
    private List<Vote> votes = new ArrayList<>();
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = false)
    private LocalDateTime votingClosedDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AgendaTopic getTopic() {
        return AgendaTopic.valueOf(topic);
    }

    public void setTopic(AgendaTopic agendaTopic) {
        this.topic = agendaTopic.getCode();
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
