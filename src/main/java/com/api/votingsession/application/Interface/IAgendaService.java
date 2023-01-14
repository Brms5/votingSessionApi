package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.AgendaCreateDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IAgendaService {
    ResponseEntity<Object> createNewAgenda(AgendaCreateDto agendaCreateDto);
    ResponseEntity<Object> removeAgendaById(UUID id);
    ResponseEntity<Object> updateAgendaById(UUID id, AgendaCreateDto agendaCreateDto);
}
