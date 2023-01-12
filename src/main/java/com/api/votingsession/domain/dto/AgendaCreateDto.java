package com.api.votingsession.domain.dto;

import com.api.votingsession.domain.Enum.AgendaTopic;
import lombok.Builder;

@Builder
public class AgendaCreateDto {

//    @NotBlank
    private AgendaTopic topic;

    private String title;

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
