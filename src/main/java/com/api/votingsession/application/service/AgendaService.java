package com.api.votingsession.application.service;

import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.Utility.CustomException.MessageBusiness;
import com.api.votingsession.application.Interface.IAgendaService;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import com.api.votingsession.Repository.AgendaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AgendaService implements IAgendaService {

    final AgendaRepository agendaRepository;

    final UserRepository userRepository;

    private static final String NOT_FOUND_MESSAGE = "Agenda not found!";

    public AgendaService(AgendaRepository agendaRepository, UserRepository userRepository) {
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<Object> createNewAgenda(AgendaCreateDto agendaCreateDto) {
        Optional<User> user = userRepository.findById(agendaCreateDto.getUserId());
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageBusiness.USER_NOT_FOUND.getMessage());

        var agenda = new Agenda();
        BeanUtils.copyProperties(agendaCreateDto, agenda);
        ArrayList<Vote> voteList = new ArrayList<>();
        agenda.setVotes(voteList);
        agenda.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        agenda.setVotingClosedDate(agenda.getRegistrationDate().plusMinutes(10));

        List<Agenda> agendaList = new ArrayList<>();
        agendaList.add(agenda);
        user.get().setAgenda(agendaList);

        return ResponseEntity.status(HttpStatus.CREATED).body(agendaRepository.save(agenda));
    }

    @Transactional
    public ResponseEntity<Object> removeAgendaById(UUID id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);

        if (agenda.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);

        agendaRepository.delete(agenda.get());
        return ResponseEntity.status(HttpStatus.OK).body("Agenda deleted successfully!");
    }

    @Transactional
    public ResponseEntity<Object> updateAgendaById(UUID id, AgendaCreateDto agendaCreateDto) {
        Optional<Agenda> agenda = agendaRepository.findById(id);

        if (agenda.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);

        var newAgenda = new Agenda();
        BeanUtils.copyProperties(agendaCreateDto, newAgenda);

        newAgenda.setId(agenda.get().getId());
        newAgenda.setVotes(agenda.get().getVotes());
        newAgenda.setRegistrationDate(agenda.get().getRegistrationDate());
        newAgenda.setVotingClosedDate(agenda.get().getVotingClosedDate());

        return ResponseEntity.status(HttpStatus.OK).body(agendaRepository.save(newAgenda));
    }
}
