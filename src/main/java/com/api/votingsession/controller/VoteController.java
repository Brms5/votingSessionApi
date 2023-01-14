package com.api.votingsession.controller;

import com.api.votingsession.application.service.VoteService;
import com.api.votingsession.domain.dto.VoteCreateDto;
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
    public ResponseEntity<Object> getAllVotesByAgenda(@PathVariable(value = "id") UUID id) {
        return voteService.getAllVotesByAgenda(id);
    }

    @PostMapping
    @ApiOperation(value = "Create new vote for an agenda", notes = "Create new user vote for a specific agenda")
    public ResponseEntity<Object> createNewVote(@RequestBody @Valid VoteCreateDto voteCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.createNewVote(voteCreateDto));
    }
}
