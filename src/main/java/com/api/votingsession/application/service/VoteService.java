package com.api.votingsession.application.service;

import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.Repository.VoteRepository;
import com.api.votingsession.application.Interface.IVoteService;
import com.api.votingsession.domain.Enum.VoteOption;
import com.api.votingsession.domain.dto.ResultVoteDto;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService implements IVoteService {

    final VoteRepository voteRepository;
    final AgendaRepository agendaRepository;
    final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, AgendaRepository agendaRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
    }

    public ResultVoteDto getAllVotesByAgenda(Optional<Agenda> agenda) {
        List<Vote> agendaVotes = agenda.get().getVotes();
        ResultVoteDto totalVotes = new ResultVoteDto();
        totalVotes.setTitle(agenda.get().getTitle());

        for (Vote vote : agendaVotes) {
            var voteEnum = vote.getVote();
            if (voteEnum == VoteOption.SIM) {
                totalVotes.setVoteYes(totalVotes.getVoteYes() + 1);
            } else {
                totalVotes.setVoteNo(totalVotes.getVoteNo() + 1);
            }
        }

        return totalVotes;
    }

    @Transactional
    public Vote createNewVote(Optional<Agenda> agenda, Optional<User> user, VoteCreateDto voteCreateDto) {
        Vote vote = new Vote();
        BeanUtils.copyProperties(voteCreateDto, vote);
        vote.setUserName(user.get().getName());
        vote.setAgendaTitle(agenda.get().getTitle());

        var voteList = agenda.get().getVotes();
        voteList.add(vote);

        agenda.get().setVotes(voteList);
        agendaRepository.save(agenda.get());

        return voteRepository.save(vote);
    }
}
