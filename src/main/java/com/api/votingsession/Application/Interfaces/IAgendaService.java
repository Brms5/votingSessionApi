package com.api.votingsession.Application.Interfaces;

import com.api.votingsession.Domain.Dtos.AgendaCreateDto;
import com.api.votingsession.Domain.Models.Agenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IAgendaService {
    Agenda CreateNewAgenda(AgendaCreateDto agendaCreateDto);
    Page<Agenda> GetAllAgenda(Pageable pageable);
    ResponseEntity<Object> GetAgendaById(UUID id);
    ResponseEntity<Object> RemoveAgendaById(UUID id);
    ResponseEntity<Object> UpdateAgendaById(AgendaCreateDto agendaCreateDto, UUID id);
    ResponseEntity<Object> GetAllVotesByAgenda(UUID id);
}
