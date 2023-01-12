package com.api.votingsession.domain.model;

import com.api.votingsession.domain.Enum.VoteOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "TB_VOTE", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@NoArgsConstructor
public class Vote implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String agendaTitle;

    @Column
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column
    private VoteOption vote;

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
        return vote;
    }

    public void setVote(VoteOption vote) {
        this.vote = vote;
    }
}
