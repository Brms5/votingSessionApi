package com.api.votingsession.Application.Services;

import com.api.votingsession.Domain.Dtos.AgendaSessionDto;
import com.api.votingsession.Domain.Models.Agenda;
import com.api.votingsession.Domain.Models.AgendaSession;
import com.api.votingsession.Infrastructure.Repositories.AgendaRepository;
import com.api.votingsession.Infrastructure.Repositories.AgendaSessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgendaSessionService {

    final AgendaSessionRepository agendaSessionRepository;
    final AgendaRepository agendaRepository;

    public AgendaSessionService(AgendaSessionRepository agendaSessionRepository, AgendaRepository agendaRepository) {
        this.agendaSessionRepository = agendaSessionRepository;
        this.agendaRepository = agendaRepository;
    }

    @Transactional
    public ResponseEntity<Object> UpdateAgendaSessionVoting(UUID id){
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voting Session not found!");
        }

        var agendaSession = agendaOptional.get().getAgendaSession();

        var agendaSessionUpdated = new AgendaSession();
        agendaSessionUpdated.setId(agendaSession.getId());
        agendaSessionUpdated.setVoting(!agendaSession.getVoting());
        agendaSessionUpdated.setVotes(agendaSession.getVotes());

        return ResponseEntity.status(HttpStatus.OK).body(agendaSessionRepository.save(agendaSessionUpdated));
    }
}
