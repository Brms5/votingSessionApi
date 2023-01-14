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
        Optional<Agenda> agenda = agendaRepository.findById(voteCreateDto.getAgendaId());
        if (agenda.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");

        var isVotingSessionClosed = agenda.get().getVotingClosedDate().isBefore(LocalDateTime.now(ZoneId.of("UTC")));
        if (isVotingSessionClosed)
            throw MessageBusiness.VOTING_SESSION_CLOSED.createException(agenda.get().getTitle());

        Optional<User> user = userRepository.findById(voteCreateDto.getUserId());
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");

        var agendaVotes = agenda.get().getVotes();

        for (Vote vote : agendaVotes) {
            var userAlreadyVoted = vote.getUserName().equals(user.get().getName());
            if (userAlreadyVoted) {
                throw MessageBusiness.ALREADY_VOTED.createException(user.get().getName());
            }
        }

        Vote vote = new Vote();
        BeanUtils.copyProperties(voteCreateDto, vote);
        vote.setUserName(user.get().getName());
        vote.setAgendaTitle(agenda.get().getTitle());

        voteRepository.save(vote);

        var voteList = agenda.get().getVotes();
        voteList.add(vote);

        agenda.get().setVotes(voteList);
        agendaRepository.save(agenda.get());

        return ResponseEntity.status(HttpStatus.OK).body("Vote created successfully!");
    }
}
