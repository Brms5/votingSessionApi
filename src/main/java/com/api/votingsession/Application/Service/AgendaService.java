package com.api.votingsession.Application.Service;

import com.api.votingsession.Application.Interface.IAgendaService;
import com.api.votingsession.Domain.Dto.AgendaCreateDto;
import com.api.votingsession.Domain.Dto.ResultVoteDto;
import com.api.votingsession.Domain.Enum.VoteOption;
import com.api.votingsession.Domain.Model.Agenda;
import com.api.votingsession.Domain.Model.Vote;
import com.api.votingsession.Repository.AgendaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgendaService implements IAgendaService {

    final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Transactional
    public Agenda CreateNewAgenda(AgendaCreateDto agendaCreateDto) {

        var agenda = new Agenda();
        BeanUtils.copyProperties(agendaCreateDto, agenda);

        ArrayList<Vote> voteList = new ArrayList<>();
        agenda.setVotes(voteList);

        agenda.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        agenda.setVotingClosedDate(agenda.getRegistrationDate().plusMinutes(10));

        return agendaRepository.save(agenda);
    }

    public ResponseEntity<Object> GetAgendaById(UUID id) {

        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(agendaOptional.get());
    }

    @Transactional
    public ResponseEntity<Object> RemoveAgendaById(UUID id) {

        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        agendaRepository.delete(agendaOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Agenda deleted successfully!");
    }

    @Transactional
    public ResponseEntity<Object> UpdateAgendaById(AgendaCreateDto agendaCreateDto, UUID id) {
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        var agenda = new Agenda();
        BeanUtils.copyProperties(agendaCreateDto, agenda);
        agenda.setId(agendaOptional.get().getId());
        agenda.setVotes(agendaOptional.get().getVotes());
        agenda.setRegistrationDate(agendaOptional.get().getRegistrationDate());
        agenda.setVotingClosedDate(agendaOptional.get().getVotingClosedDate());

        return ResponseEntity.status(HttpStatus.OK).body(agendaRepository.save(agenda));
    }

    public ResponseEntity<Object> GetAllVotesByAgenda(UUID id) {
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        var agendaVotes = agendaOptional.get().getVotes();

        ResultVoteDto totalVotes = new ResultVoteDto();
        totalVotes.setTitle(agendaOptional.get().getTitle());

        for (Vote vote : agendaVotes) {
            var voteEnum = vote.getVote();
            if (voteEnum == VoteOption.SIM) {
                totalVotes.setVoteYes(totalVotes.getVoteYes() + 1);
            } else {
                totalVotes.setVoteNo(totalVotes.getVoteNo() + 1);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(totalVotes);
    }
}
