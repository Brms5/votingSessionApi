package com.api.votingsession.Application.Interfaces;

import com.api.votingsession.Domain.Dtos.VoteCreateDto;
import org.springframework.http.ResponseEntity;

public interface IVoteService {
    ResponseEntity<Object> CreateNewVote(VoteCreateDto voteCreateDto);
}
