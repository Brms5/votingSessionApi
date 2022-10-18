package com.api.votingsession.Domain.Dtos;

import com.api.votingsession.Domain.Enums.AgendaTopic;
import com.api.votingsession.Domain.Models.VotingSession;

import javax.validation.constraints.NotBlank;

public class AgendaDto {

//    @NotBlank
    private AgendaTopic topic;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private VotingSession votingSession;

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

    public VotingSession getVotingSession() { return votingSession; }

    public void setVotingSession(VotingSession votingSession) {
        this.votingSession = votingSession;
    }
}
