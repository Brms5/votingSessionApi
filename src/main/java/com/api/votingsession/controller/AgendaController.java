package com.api.votingsession.controller;

import com.api.votingsession.application.service.AgendaService;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.Repository.AgendaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/agenda")
public class AgendaController {

    final AgendaService agendaService;

    final AgendaRepository agendaRepository;

    private static final String NOT_FOUND_MESSAGE = "Agenda not found!";

    public AgendaController(AgendaService agendaService, AgendaRepository agendaRepository) {
        this.agendaService = agendaService;
        this.agendaRepository = agendaRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createNewAgenda(@RequestBody @Valid AgendaCreateDto agendaCreateDto) {
        return agendaService.createNewAgenda(agendaCreateDto);
    }

    @GetMapping
    public ResponseEntity<Page<Agenda>> getAllAgendas(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(agendaRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAgendaById(@PathVariable(value = "id") UUID id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if (agenda.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(agenda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeAgendaById(@PathVariable(value = "id") UUID id) {
        return agendaService.removeAgendaById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAgendaById(@PathVariable(value = "id") UUID id, @RequestBody @Valid AgendaCreateDto agendaCreateDto) {
        return agendaService.updateAgendaById(id, agendaCreateDto);
    }

    @GetMapping("/voting-result/{id}")
    public ResponseEntity<Object> getAllVotesByAgenda(@PathVariable(value = "id") UUID id) {
        return agendaService.getAllVotesByAgenda(id);
    }
}
