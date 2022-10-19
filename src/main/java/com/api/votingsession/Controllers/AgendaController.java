package com.api.votingsession.Controllers;

import com.api.votingsession.Application.Services.AgendaService;
import com.api.votingsession.Domain.Dtos.AgendaDto;
import com.api.votingsession.Domain.Models.Agenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/agenda")
public class AgendaController {

    final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public ResponseEntity<Agenda> CreateNewAgenda(@RequestBody @Valid AgendaDto agendaDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.CreateNewAgenda(agendaDto));
    }

    @GetMapping
    public ResponseEntity<Page<Agenda>> GetAllAgenda(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable ){
        return ResponseEntity.status(HttpStatus.OK).body(agendaService.GetAllAgenda(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> GetAgendaById(@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(agendaService.GetAgendaById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> RemoveAgendaById(@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(agendaService.RemoveAgendaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateAgendaById(@PathVariable(value = "id") UUID id, @RequestBody @Valid AgendaDto agendaDto){
        return ResponseEntity.status(HttpStatus.OK).body(agendaService.UpdateAgendaById(agendaDto, id));
    }
}
