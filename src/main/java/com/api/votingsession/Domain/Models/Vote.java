package com.api.votingsession.Domain.Models;

import com.api.votingsession.Domain.Enums.AgendaTopic;
import com.api.votingsession.Domain.Enums.VoteOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_VOTE", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Vote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String agendaTitle;
    @Column
    private String userName;
    @Column
    private Integer vote;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAgendaTitle() {
        return agendaTitle;
    }

    public void setAgendaTitle(String agendaTitle) {
        this.agendaTitle = agendaTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public VoteOption getVote() {
        return VoteOption.valueOf(vote);
    }

    public void setVote(VoteOption voteOption) {
        this.vote = voteOption.getCode();
    }
}
