package com.api.votingsession.controller;

import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.Utility.CustomException.MessageBusiness;
import com.api.votingsession.Utility.ResponsePageable.CustomPage;
import com.api.votingsession.application.service.AgendaService;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
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

    final UserRepository userRepository;

    public AgendaController(AgendaService agendaService, AgendaRepository agendaRepository, UserRepository userRepository) {
        this.agendaService = agendaService;
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
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
    public ResponseEntity<Object> getAgendaById(@PathVariable(value = "id") UUID id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if (agenda.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageBusiness.AGENDA_NOT_FOUND.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(agenda);
    }

    @PostMapping
    @ApiOperation(value = "Create new agenda", notes = "Create a new agenda to vote")
    public ResponseEntity<Object> createNewAgenda(@RequestBody @Valid AgendaCreateDto agendaCreateDto) {
        Optional<User> user = userRepository.findById(agendaCreateDto.getUserId());
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageBusiness.USER_NOT_FOUND.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.createNewAgenda(agendaCreateDto, user));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update agenda by ID", notes = "Update a specific agenda")
    public ResponseEntity<Object> updateAgendaById(@PathVariable(value = "id") UUID id, @RequestBody @Valid AgendaCreateDto agendaCreateDto) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if (agenda.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageBusiness.AGENDA_NOT_FOUND.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(agendaService.updateAgendaById(agendaCreateDto, agenda));
    }

}
