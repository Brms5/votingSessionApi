package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;

import java.util.UUID;

public interface IAgendaService {
    Agenda createNewAgenda(AgendaCreateDto agendaCreateDto);
    Agenda updateAgendaById(UUID id, AgendaCreateDto agendaCreateDto);
    String removeAgendaById(UUID id);
}
