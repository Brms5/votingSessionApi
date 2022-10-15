package com.api.votingsession.Domain.Dtos;

import com.api.votingsession.Domain.Enums.AgendaTopic;

import javax.validation.constraints.NotBlank;

public class AgendaDto {

//    @NotBlank
    private AgendaTopic topic;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

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
}
