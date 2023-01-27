package com.api.votingsession.application.service;

import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.Repository.VoteRepository;
import com.api.votingsession.Utility.CustomException.BusinessException;
import com.api.votingsession.domain.Enum.AgendaTopic;
import com.api.votingsession.domain.Enum.VoteOption;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.dto.ResultVoteDto;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteServiceTest {

    @MockBean
    private VoteRepository voteRepository;

    @MockBean
    private AgendaRepository agendaRepository;

    @MockBean
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<Vote> voteArgumentCaptor;

    @Captor
    ArgumentCaptor<Agenda> agendaArgumentCaptor;

    @Autowired
    private VoteService voteService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    private static final AgendaTopic BASE_AGENDA_TOPIC = AgendaTopic.generateRandomTopic();

    private static final String TEST_TITLE = generateRandomString();

    private static final String TEST_DESCRIPTION = generateRandomString();

    private static final LocalDateTime TEST_REGISTRATION_DATE = LocalDateTime.now();

    private static final String TEST_NAME = generateRandomString();

    private static final VoteOption TEST_VOTE = VoteOption.generateRandomVoteOption();


    private static String generateRandomString() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private AgendaCreateDto buildAgendaCreateDto() {
        return AgendaCreateDto.builder()
                .topic(BASE_AGENDA_TOPIC)
                .title(TEST_TITLE)
                .description(TEST_DESCRIPTION)
                .build();
    }

    private User buildUser() {
        List<Agenda> agendaList = new ArrayList<>();
        return User.builder()
                .id(UUID.randomUUID())
                .name(TEST_NAME)
                .agenda(agendaList)
                .build();
    }

    private Vote buildVote() {
        AgendaCreateDto agendaCreateDto = buildAgendaCreateDto();
        User user = buildUser();
        return Vote.builder()
                .id(UUID.randomUUID())
                .agendaTitle(agendaCreateDto.getTitle())
                .userName(user.getName())
                .vote(TEST_VOTE)
                .build();
    }

    private Agenda buildAgenda() {
        AgendaCreateDto agendaCreateDto = buildAgendaCreateDto();
        ArrayList<Vote> voteList = new ArrayList<>();
        return Agenda.builder()
                .id(UUID.randomUUID())
                .topic(agendaCreateDto.getTopic())
                .title(agendaCreateDto.getTitle())
                .description(agendaCreateDto.getDescription())
                .votes(voteList)
                .registrationDate(TEST_REGISTRATION_DATE)
                .votingClosedDate(TEST_REGISTRATION_DATE.plusDays(1))
                .build();
    }

    private VoteCreateDto buildVoteCreateDto() {
        Agenda agenda = buildAgenda();
        User user = buildUser();
        return VoteCreateDto.builder().agendaId(agenda.getId()).userId(user.getId()).vote(TEST_VOTE).build();
    }

    @Test
    public void getAllVotesByAgendaTest() {
        Agenda agenda = buildAgenda();
        ResultVoteDto resultVoteDto = new ResultVoteDto();

        when(agendaRepository.findById(agenda.getId())).thenReturn(Optional.of(agenda));

        ResultVoteDto response = voteService.getAllVotesByAgenda(agenda.getId());

        Assertions.assertThat(agenda.getVotes()).isNotNull();
        Assertions.assertThat(response.getTitle()).isNotNull();
        Assertions.assertThat(response.getVoteYes()).isNotNull();
        Assertions.assertThat(response.getVoteNo()).isNotNull();
        assertSame(response.getClass(), resultVoteDto.getClass());
    }

    @Test
    public void createNewVoteSuccessTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        User user = buildUser();
        Vote vote = buildVote();

        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        when(userRepository.findById(voteCreateDto.getUserId())).thenReturn(Optional.of(user));
        when(voteRepository.save(any(Vote.class))).thenAnswer(i -> i.getArguments()[0]);

        Vote response = voteService.createNewVote(voteCreateDto);

        Mockito.verify(voteRepository).save(voteArgumentCaptor.capture());
        Vote voteSaved = voteArgumentCaptor.getValue();
        Assertions.assertThat(voteSaved.getUserName()).isNotNull();
        Assertions.assertThat(voteSaved.getAgendaTitle()).isNotNull();
        Mockito.verify(agendaRepository).save(agendaArgumentCaptor.capture());
        Agenda agendaSaved = agendaArgumentCaptor.getValue();
        Assertions.assertThat(agendaSaved.getVotes()).isNotNull();
        assertEquals(vote.getClass(), response.getClass());
    }

    @Test
    public void createNewVoteAgendaNotFoundTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        BusinessException exception = assertThrows(BusinessException.class, () -> voteService.createNewVote(voteCreateDto));
        assertTrue(exception.getMessage().contentEquals("Agenda not found!"));
    }

    @Test
    public void createNewVoteVotingSessionClosedTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        agenda.setVotingClosedDate(LocalDateTime.now());
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        BusinessException exception = assertThrows(BusinessException.class, () -> voteService.createNewVote(voteCreateDto));
        assertTrue(exception.getMessage().contentEquals("Voting Session is closed!"));
    }

    @Test
    public void createNewVoteUserNotFoundTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        BusinessException exception = assertThrows(BusinessException.class, () -> voteService.createNewVote(voteCreateDto));
        assertTrue(exception.getMessage().contentEquals("User not found!"));
    }

    @Test
    public void createNewVoteUserCannotVoteTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        ArrayList<Agenda> agendaList = new ArrayList<>();
        agendaList.add(agenda);
        User user = buildUser();
        user.setAgenda(agendaList);

        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        when(userRepository.findById(voteCreateDto.getUserId())).thenReturn(Optional.of(user));

        BusinessException exception = assertThrows(BusinessException.class, () -> voteService.createNewVote(voteCreateDto));

        assertTrue(exception.getMessage().contentEquals("The user cannot vote on the agenda he created."));
    }

    @Test
    public void createNewVoteUserAlreadyVotedTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        User user = buildUser();
        Vote vote = buildVote();
        vote.setUserName(user.getName());
        ArrayList<Vote> voteList = new ArrayList<>();
        voteList.add(vote);
        Agenda agenda = buildAgenda();
        agenda.setVotes(voteList);
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        when(userRepository.findById(voteCreateDto.getUserId())).thenReturn(Optional.of(user));
        BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class, () -> voteService.createNewVote(voteCreateDto));
        assertTrue(exception.getMessage().contentEquals("User already voted!"));
    }

}

