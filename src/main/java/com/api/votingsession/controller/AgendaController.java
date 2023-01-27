package com.api.votingsession.controller;

import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.Utility.CustomException.MessageBusiness;
import com.api.votingsession.Utility.ResponsePageable.CustomPage;
import com.api.votingsession.application.service.AgendaService;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/agenda")
public class AgendaController {

    final AgendaService agendaService;

    final AgendaRepository agendaRepository;

    public AgendaController(AgendaService agendaService, AgendaRepository agendaRepository) {
        this.agendaService = agendaService;
        this.agendaRepository = agendaRepository;
    }

    @GetMapping
    @ApiOperation(value = "Request all agendas", notes = "Search for all agendas")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataTypeClass = Integer.class, paramType = "query",
                    value = "Results page you want to retrieve"),
            @ApiImplicitParam(name = "size", dataTypeClass = Integer.class, paramType = "query",
                    value = "Number of records per page.")
    })
    public ResponseEntity<CustomPage<Agenda>> getAllAgendas(@ApiIgnore @PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomPage<>(agendaRepository.findAll(pageable)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Request agenda by ID", notes = "Search for a specific agenda")
    public ResponseEntity<Optional<Agenda>> getAgendaById(@PathVariable(value = "id") UUID id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if (agenda.isEmpty())
            throw MessageBusiness.AGENDA_NOT_FOUND.createException();
        return ResponseEntity.status(HttpStatus.OK).body(agenda);
    }

    @PostMapping
    @ApiOperation(value = "Create new agenda", notes = "Create a new agenda to vote")
    public ResponseEntity<Agenda> createNewAgenda(@RequestBody @Valid AgendaCreateDto agendaCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.createNewAgenda(agendaCreateDto));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update agenda by ID", notes = "Update a specific agenda")
    public ResponseEntity<Agenda> updateAgendaById(@PathVariable(value = "id") UUID id, @RequestBody @Valid AgendaCreateDto agendaCreateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(agendaService.updateAgendaById(id, agendaCreateDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete agenda by ID", notes = "Delete a specific agenda")
    public ResponseEntity<String> removeAgendaById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(agendaService.removeAgendaById(id));
    }

}
