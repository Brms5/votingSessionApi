package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IAgendaService {
    Agenda CreateNewAgenda(AgendaCreateDto agendaCreateDto);
    ResponseEntity<Object> GetAgendaById(UUID id);
    ResponseEntity<Object> RemoveAgendaById(UUID id);
    ResponseEntity<Object> UpdateAgendaById(AgendaCreateDto agendaCreateDto, UUID id);
    ResponseEntity<Object> GetAllVotesByAgenda(UUID id);
}
