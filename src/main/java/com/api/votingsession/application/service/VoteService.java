package com.api.votingsession.application.service;

import com.api.votingsession.Utility.CustomException.MessageBusiness;
import com.api.votingsession.application.Interface.IVoteService;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.Repository.VoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class VoteService implements IVoteService {

    final VoteRepository voteRepository;
    final AgendaRepository agendaRepository;
    final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, AgendaRepository agendaRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<Object> createNewVote(VoteCreateDto voteCreateDto) {
        Optional<Agenda> agendaOptional = agendaRepository.findById(voteCreateDto.getAgendaId());
        if (agendaOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");

        var isVotingSessionClosed = agendaOptional.get().getVotingClosedDate().isBefore(LocalDateTime.now(ZoneId.of("UTC")));
        if (isVotingSessionClosed)
            throw MessageBusiness.VOTING_SESSION_CLOSED.createException(agendaOptional.get().getTitle());

        Optional<User> userOptional = userRepository.findById(voteCreateDto.getUserId());

        if (userOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");

        var agendaVotes = agendaOptional.get().getVotes();

        for (Vote vote: agendaVotes) {
            var userAlreadyVoted = vote.getUserName().equals(userOptional.get().getName());
            if (userAlreadyVoted) {
                throw MessageBusiness.ALREADY_VOTED.createException(userOptional.get().getName());
            }
        }

        Vote vote = new Vote();
        BeanUtils.copyProperties(voteCreateDto, vote);
        vote.setUserName(userOptional.get().getName());
        vote.setAgendaTitle(agendaOptional.get().getTitle());

        voteRepository.save(vote);

        var voteList = agendaOptional.get().getVotes();
        voteList.add(vote);

        agendaOptional.get().setVotes(voteList);
        agendaRepository.save(agendaOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Vote created successfully!");
    }
}
