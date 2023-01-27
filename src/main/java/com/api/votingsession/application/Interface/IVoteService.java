package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.ResultVoteDto;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Vote;

import java.util.UUID;

public interface IVoteService {
    ResultVoteDto getAllVotesByAgenda(UUID id);
    Vote createNewVote(VoteCreateDto voteCreateDto);
}
