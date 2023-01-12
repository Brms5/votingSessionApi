package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.VoteCreateDto;
import org.springframework.http.ResponseEntity;

public interface IVoteService {
    ResponseEntity<Object> CreateNewVote(VoteCreateDto voteCreateDto) throws Exception;
}
