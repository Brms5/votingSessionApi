package com.api.votingsession.controller;

import com.api.votingsession.application.service.VoteService;
import com.api.votingsession.domain.dto.VoteCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vote")
public class VoteController {

    final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Object> createNewVote(@RequestBody @Valid VoteCreateDto voteCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.createNewVote(voteCreateDto));
    }
}
