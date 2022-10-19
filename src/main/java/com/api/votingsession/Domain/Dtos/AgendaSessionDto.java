package com.api.votingsession.Domain.Dtos;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class AgendaSessionDto {

//    @NotBlank
    private Boolean voting;

    public Boolean getVoting() {
        return voting;
    }

    public void setVoting(Boolean voting) {
        this.voting = voting;
    }
}
