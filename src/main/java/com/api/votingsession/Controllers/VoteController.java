package com.api.votingsession.Controllers;

import com.api.votingsession.Application.Services.VoteService;
import com.api.votingsession.Domain.Dtos.VoteCreateDto;
import com.api.votingsession.Domain.Models.Vote;
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
    public ResponseEntity<Object> CreateNewVote(@RequestBody @Valid VoteCreateDto voteCreateDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.CreateNewVote(voteCreateDto));
    }
}
