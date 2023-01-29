package com.api.votingsession.controller;

import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.Utility.CustomException.MessageBusiness;
import com.api.votingsession.application.service.VoteService;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vote")
public class VoteController {

    final VoteService voteService;

    final AgendaRepository agendaRepository;

    final UserRepository userRepository;

    public VoteController(VoteService voteService, AgendaRepository agendaRepository, UserRepository userRepository) {
        this.voteService = voteService;
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/voting-result/{id}")
    @ApiOperation(value = "Request the result voting session from a agenda", notes = "Search for the result voting session from a specific agenda")
    public ResponseEntity<Object> getAllVotesByAgenda(@PathVariable(value = "id") UUID id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if (agenda.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageBusiness.AGENDA_NOT_FOUND.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(voteService.getAllVotesByAgenda(agenda));
    }

    @PostMapping
    @ApiOperation(value = "Create new vote for an agenda",
            notes = "Create new vote for a specific agenda. In the Voting Session, Agendas only can be voted for 1 day. So from the moment the Agenda is created, there is only one more day to vote on it.")
    public ResponseEntity<Object> createNewVote(@RequestBody @Valid VoteCreateDto voteCreateDto) {
        Optional<Agenda> agenda = agendaRepository.findById(voteCreateDto.getAgendaId());
        if (agenda.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageBusiness.AGENDA_NOT_FOUND.getMessage());

        var isVotingSessionClosed = agenda.get().getVotingClosedDate().isBefore(LocalDateTime.now(ZoneId.of("UTC")));
        if (isVotingSessionClosed)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(MessageBusiness.VOTING_SESSION_CLOSED.getMessage());

        Optional<User> user = userRepository.findById(voteCreateDto.getUserId());
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageBusiness.USER_NOT_FOUND.getMessage());

        if (!user.get().getAgenda().isEmpty()) {
            List<Agenda> agendaList = user.get().getAgenda();
            for ( Agenda eachAgenda : agendaList ) {
                if (eachAgenda.getId().equals(agenda.get().getId()))
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(MessageBusiness.USER_CANNOT_VOTE.getMessage());
            }
        }

        List<Vote> agendaVotes = agenda.get().getVotes();
        for (Vote vote : agendaVotes) {
            var userAlreadyVoted = vote.getUserName().equals(user.get().getName());
            if (userAlreadyVoted) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(MessageBusiness.ALREADY_VOTED.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.createNewVote(agenda, user, voteCreateDto));
    }
}
