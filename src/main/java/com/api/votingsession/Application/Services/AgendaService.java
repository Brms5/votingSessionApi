package com.api.votingsession.Application.Services;

import com.api.votingsession.Domain.Dtos.AgendaCreateDto;
import com.api.votingsession.Domain.Models.Agenda;
import com.api.votingsession.Infrastructure.Repositories.AgendaRepository;
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

    public AgendaService(AgendaRepository agendaRepository){
        this.agendaRepository = agendaRepository;
    }

    @Transactional
    public Agenda CreateNewAgenda(AgendaCreateDto agendaCreateDto){

        var agenda = new Agenda();
        BeanUtils.copyProperties(agendaCreateDto, agenda);
        agenda.setVotes(null);
        agenda.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        agenda.setVotingClosedDate(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(1));

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

        if(agendaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        agendaRepository.delete(agendaOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Agenda deleted successfully!");
    }

    @Transactional
    public ResponseEntity<Object> UpdateAgendaById(AgendaCreateDto agendaCreateDto, UUID id){
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        }

        var agenda = new Agenda();
        BeanUtils.copyProperties(agendaCreateDto, agenda);
        agenda.setId(agendaOptional.get().getId());
        agenda.setRegistrationDate(agendaOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(agendaRepository.save(agenda));
    }
}
