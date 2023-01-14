package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.VoteCreateDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IVoteService {
    ResponseEntity<Object> getAllVotesByAgenda(UUID id);
    ResponseEntity<Object> createNewVote(VoteCreateDto voteCreateDto);
}
