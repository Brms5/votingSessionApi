package com.api.votingsession.Application.Services;

import com.api.votingsession.Domain.Dtos.VoteCreateDto;
import com.api.votingsession.Domain.Models.Agenda;
import com.api.votingsession.Domain.Models.User;
import com.api.votingsession.Domain.Models.Vote;
import com.api.votingsession.Infrastructure.Repositories.AgendaRepository;
import com.api.votingsession.Infrastructure.Repositories.UserRepository;
import com.api.votingsession.Infrastructure.Repositories.VoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    final VoteRepository voteRepository;
    final AgendaRepository agendaRepository;
    final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, AgendaRepository agendaRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<Object> CreateNewVote(VoteCreateDto voteCreateDto) {

        Optional<Agenda> agendaOptional = agendaRepository.findById(voteCreateDto.getAgendaId());

        if (agendaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        Optional<User> userOptional = userRepository.findById(voteCreateDto.getUserId());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        Vote vote = new Vote();
        BeanUtils.copyProperties(voteCreateDto, vote);
        vote.setUserName(userOptional.get().getName());
        vote.setAgendaTitle(agendaOptional.get().getTitle());

        voteRepository.save(vote);

        ArrayList<Vote> voteList = new ArrayList<>();
        voteList.add(vote);

        agendaOptional.get().setVotes(voteList);
        agendaRepository.save(agendaOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Vote created successfully!");
    }
}
