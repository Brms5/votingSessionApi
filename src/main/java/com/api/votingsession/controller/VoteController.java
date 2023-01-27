package com.api.votingsession.controller;

import com.api.votingsession.application.service.VoteService;
import com.api.votingsession.domain.dto.ResultVoteDto;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Vote;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vote")
public class VoteController {

    final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/voting-result/{id}")
    @ApiOperation(value = "Request the result voting session from a agenda", notes = "Search for the result voting session from a specific agenda")
    public ResponseEntity<ResultVoteDto> getAllVotesByAgenda(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(voteService.getAllVotesByAgenda(id));
    }

    @PostMapping
    @ApiOperation(value = "Create new vote for an agenda",
            notes = "Create new vote for a specific agenda. In the Voting Session, Agendas only can be voted for 1 day. So from the moment the Agenda is created, there is only one more day to vote on it.")
    public ResponseEntity<Vote> createNewVote(@RequestBody @Valid VoteCreateDto voteCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.createNewVote(voteCreateDto));
    }
}
