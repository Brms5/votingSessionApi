package com.api.votingsession.Domain.Dtos;

import com.api.votingsession.Domain.Enums.AgendaTopic;

import javax.validation.constraints.NotBlank;

public class AgendaCreateDto {

//    @NotBlank
    private Integer topic;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private VoteDto votes;

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

    public VoteDto getVotes() {
        return votes;
    }

    public void setVotes(VoteDto votes) {
        this.votes = votes;
    }
}
