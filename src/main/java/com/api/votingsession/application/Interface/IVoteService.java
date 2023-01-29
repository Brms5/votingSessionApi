package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.ResultVoteDto;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;

import java.util.Optional;

public interface IVoteService {
    ResultVoteDto getAllVotesByAgenda(Optional<Agenda> agenda);
    Vote createNewVote(Optional<Agenda> agenda, Optional<User> user, VoteCreateDto voteCreateDto);
}
