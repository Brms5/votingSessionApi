package com.api.votingsession.Controllers;

import com.api.votingsession.Application.Services.AgendaSessionService;
import com.api.votingsession.Domain.Dtos.AgendaSessionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/agenda-session")
public class AgendaSessionController {

    final AgendaSessionService agendaSessionService;

    public AgendaSessionController(AgendaSessionService agendaSessionService) {
        this.agendaSessionService = agendaSessionService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateAgendaSessionVoting(@PathVariable(value = "id") UUID id){

        return ResponseEntity.status(HttpStatus.OK).body(agendaSessionService.UpdateAgendaSessionVoting(id));
    }
}
