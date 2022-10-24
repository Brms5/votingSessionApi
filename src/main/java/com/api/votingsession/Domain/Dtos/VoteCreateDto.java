package com.api.votingsession.Domain.Dtos;

import com.api.votingsession.Domain.Enums.VoteOption;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class VoteCreateDto {
//    @NotBlank
    @JsonDeserialize
    private UUID agendaId;
//    @NotBlank
    @JsonDeserialize
    private UUID userId;
//    @NotBlank
    private Integer vote;

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
        return VoteOption.valueOf(vote);
    }

    public void setVote(VoteOption voteOption) {
        this.vote = voteOption.getCode();
    }
}
