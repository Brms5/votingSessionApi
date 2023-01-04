package com.api.votingsession.Application.Interface;

import com.api.votingsession.Domain.Dto.VoteCreateDto;
import org.springframework.http.ResponseEntity;

public interface IVoteService {
    ResponseEntity<Object> CreateNewVote(VoteCreateDto voteCreateDto) throws Exception;
}
