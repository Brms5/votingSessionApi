package com.api.votingsession.Domain.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_VOTING_SESSION")
public class VotingSession implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Boolean voting = false;
    private Integer votes = 0;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getVoting() {
        return voting;
    }

    public void setVoting(Boolean voting) {
        this.voting = voting;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

}
