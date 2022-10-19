package com.api.votingsession.Application.Services;

import com.api.votingsession.Domain.Dtos.AgendaDto;
import com.api.votingsession.Domain.Models.Agenda;
import com.api.votingsession.Domain.Models.VotingSession;
import com.api.votingsession.Infrastructure.Repositories.AgendaRepository;
import com.api.votingsession.Infrastructure.Repositories.VotingSessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgendaService {

    final AgendaRepository agendaRepository;
    final VotingSessionRepository votingSessionRepository;

    public AgendaService(AgendaRepository agendaRepository, VotingSessionRepository votingSessionRepository) {
        this.agendaRepository = agendaRepository;
        this.votingSessionRepository = votingSessionRepository;
    }

    @Transactional
    public Agenda CreateNewAgenda(AgendaDto agendaDto){

        var agenda = new Agenda();
        BeanUtils.copyProperties(agendaDto, agenda);
        agenda.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        var votingSession = new VotingSession();
        agenda.setVotingSession(votingSession);

        return agendaRepository.save(agenda);
    }

    public Page<Agenda> GetAllAgenda(Pageable pageable){
        return agendaRepository.findAll(pageable);
    }

    public ResponseEntity<Object> GetAgendaById(UUID id){

        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if(agendaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(agendaOptional.get());
    }

    @Transactional
    public ResponseEntity<Object> RemoveAgendaById(UUID id){

        Optional<Agenda> agendaOptional = agendaRepository.findById(id);
        var agendaVotingSession = agendaOptional.get().getVotingSession();

        if(agendaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        votingSessionRepository.delete(agendaVotingSession);
        agendaRepository.delete(agendaOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Agenda deleted successfully!");
    }

    @Transactional
    public ResponseEntity UpdateAgendaById(AgendaDto agendaDto, UUID id){
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        var agenda = new Agenda();
        BeanUtils.copyProperties(agendaDto, agenda);
        agenda.setId(agendaOptional.get().getId());
        agenda.setRegistrationDate(agendaOptional.get().getRegistrationDate());
        agenda.setVotingSession(agendaOptional.get().getVotingSession());

        return ResponseEntity.status(HttpStatus.OK).body(agendaRepository.save(agenda));
    }
}
