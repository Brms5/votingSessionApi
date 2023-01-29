package com.api.votingsession.application.service;

import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.application.Interface.IAgendaService;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgendaService implements IAgendaService {

    final AgendaRepository agendaRepository;

    final UserRepository userRepository;

    public AgendaService(AgendaRepository agendaRepository, UserRepository userRepository) {
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Agenda createNewAgenda(AgendaCreateDto agendaCreateDto, Optional<User> user) {
        Agenda agenda = new Agenda();
        BeanUtils.copyProperties(agendaCreateDto, agenda);
        agenda.setUsername(user.get().getName());
        ArrayList<Vote> voteList = new ArrayList<>();
        agenda.setVotes(voteList);
        agenda.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        agenda.setVotingClosedDate(agenda.getRegistrationDate().plusDays(1));

        List<Agenda> agendaList = new ArrayList<>();
        agendaList.add(agenda);
        user.get().setAgenda(agendaList);

        return agendaRepository.save(agenda);
    }

    @Transactional
    public Agenda updateAgendaById(AgendaCreateDto agendaCreateDto, Optional<Agenda> agenda) {
        var newAgenda = new Agenda();
        BeanUtils.copyProperties(agendaCreateDto, newAgenda);

        newAgenda.setId(agenda.get().getId());
        newAgenda.setVotes(agenda.get().getVotes());
        newAgenda.setUsername(agenda.get().getUsername());
        newAgenda.setRegistrationDate(agenda.get().getRegistrationDate());
        newAgenda.setVotingClosedDate(agenda.get().getVotingClosedDate());

        return agendaRepository.save(newAgenda);
    }

}
