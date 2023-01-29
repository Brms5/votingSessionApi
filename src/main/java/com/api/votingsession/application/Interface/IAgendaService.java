package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;

import java.util.Optional;

public interface IAgendaService {
    Agenda createNewAgenda(AgendaCreateDto agendaCreateDto, Optional<User> user);
    Agenda updateAgendaById(AgendaCreateDto agendaCreateDto, Optional<Agenda> agenda);
}
