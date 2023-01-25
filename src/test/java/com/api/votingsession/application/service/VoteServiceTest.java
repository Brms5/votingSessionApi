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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Integer randomNumber = new Random().nextInt((9) + 1);
        ResultVoteDto resultVoteDto = new ResultVoteDto();
        resultVoteDto.setTitle(generateRandomString());
        resultVoteDto.setVoteYes(randomNumber);
        resultVoteDto.setVoteNo(randomNumber);
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(resultVoteDto);

        when(agendaRepository.findById(agenda.getId())).thenReturn(Optional.of(agenda));

        ResponseEntity<ResultVoteDto> response = voteService.getAllVotesByAgenda(agenda.getId());
        ResultVoteDto responseBody = response.getBody();

        Assertions.assertThat(agenda.getVotes()).isNotNull();
        Assertions.assertThat(responseBody.getTitle()).isNotNull();
        Assertions.assertThat(responseBody.getVoteYes()).isNotNull();
        Assertions.assertThat(responseBody.getVoteNo()).isNotNull();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createNewVoteSuccessTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        User user = buildUser();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body("Vote created successfully!");

        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        when(userRepository.findById(voteCreateDto.getUserId())).thenReturn(Optional.of(user));

        ResponseEntity<Object> response = voteService.createNewVote(voteCreateDto);

        Mockito.verify(voteRepository).save(voteArgumentCaptor.capture());
        Vote voteSaved = voteArgumentCaptor.getValue();
        Assertions.assertThat(voteSaved.getUserName()).isNotNull();
        Assertions.assertThat(voteSaved.getAgendaTitle()).isNotNull();
        Mockito.verify(agendaRepository).save(agendaArgumentCaptor.capture());
        Agenda agendaSaved = agendaArgumentCaptor.getValue();
        Assertions.assertThat(agendaSaved.getVotes()).isNotNull();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteAgendaNotFoundTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        ResponseEntity<Object> response = voteService.createNewVote(voteCreateDto);
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteVotingSessionClosedTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        agenda.setVotingClosedDate(LocalDateTime.now());
//        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body("Vote created successfully!");
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
//        Mockito.doThrow(new BusinessException(HttpStatus.BAD_REQUEST, "Voting Session is closed!", "The selected Agenda: %s has already been voted."))
//                .when(voteService).createNewVote(voteCreateDto);
        BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class, () -> voteService.createNewVote(voteCreateDto));
//        ResponseEntity<Object> response = voteService.createNewVote(voteCreateDto);
//        assertEquals("Voting Session is closed!", exception.getMessage());
        assertTrue(exception.getMessage().contentEquals("Voting Session is closed!"));
    }

    @Test
    public void createNewVoteUserNotFoundTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        ResponseEntity<Object> response = voteService.createNewVote(voteCreateDto);
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteUserCannotVoteTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        ArrayList<Agenda> agendaList = new ArrayList<>();
        agendaList.add(agenda);
        User user = buildUser();
        user.setAgenda(agendaList);
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user cannot vote on the agenda he created.");

        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        when(userRepository.findById(voteCreateDto.getUserId())).thenReturn(Optional.of(user));

        ResponseEntity<Object> response = voteService.createNewVote(voteCreateDto);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
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

