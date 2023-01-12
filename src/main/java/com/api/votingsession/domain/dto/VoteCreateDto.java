package com.api.votingsession.domain.dto;

import com.api.votingsession.domain.Enum.VoteOption;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;

public class VoteCreateDto {
    //    @NotBlank
    @JsonDeserialize
    private UUID agendaId;
    //    @NotBlank
    @JsonDeserialize
    private UUID userId;
    //    @NotBlank
    private VoteOption vote;

    public UUID getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(UUID agendaId) {
        this.agendaId = agendaId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public VoteOption getVote() {
        return vote;
    }

    public void setVote(VoteOption vote) {
        this.vote = vote;
    }

}
