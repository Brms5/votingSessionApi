package com.api.votingsession.controller;

import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.application.service.VoteService;
import com.api.votingsession.domain.Enum.AgendaTopic;
import com.api.votingsession.domain.Enum.VoteOption;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.dto.ResultVoteDto;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {

    @Autowired
    private VoteController voteController;

    @MockBean
    private VoteService voteService;

    @MockBean
    private AgendaRepository agendaRepository;

    @MockBean
    private UserRepository userRepository;

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
        return User.builder()
                .id(UUID.randomUUID())
                .name(TEST_NAME)
                .agenda(new ArrayList<>())
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
        Vote vote = buildVote();
        ArrayList<Vote> voteList = new ArrayList<>();
        voteList.add(vote);
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
    public void getAllVotesByAgendaSuccessTest() {
        Agenda agenda = buildAgenda();
        Integer randomNumber = new Random().nextInt((9) + 1);
        ResultVoteDto resultVoteDto = new ResultVoteDto();
        resultVoteDto.setTitle(generateRandomString());
        resultVoteDto.setVoteYes(randomNumber);
        resultVoteDto.setVoteNo(randomNumber);
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(resultVoteDto);
        Mockito.when(agendaRepository.findById(agenda.getId())).thenReturn(Optional.of(agenda));
        Mockito.when(voteService.getAllVotesByAgenda(Optional.of(agenda))).thenReturn(resultVoteDto);
        ResponseEntity<Object> response = voteController.getAllVotesByAgenda(agenda.getId());
        Assertions.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void getAllVotesByAgendaNotFoundTest() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        ResponseEntity<Object> response = voteController.getAllVotesByAgenda(UUID.randomUUID());
        Assertions.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteSuccessTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        User user = buildUser();
        Vote vote = buildVote();
        vote.setUserName(generateRandomString());
        ArrayList<Vote> voteList = new ArrayList<>();
        voteList.add(vote);
        agenda.setVotes(voteList);
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(vote);
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        when(userRepository.findById(voteCreateDto.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(voteService.createNewVote(Optional.of(agenda), Optional.of(user), voteCreateDto)).thenReturn(vote);
        ResponseEntity<Object> response = voteController.createNewVote(voteCreateDto);
        Assertions.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteAgendaNotFoundTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        ResponseEntity<Object> response = voteController.createNewVote(voteCreateDto);
        Assertions.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteVotingSessionClosedTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        agenda.setVotingClosedDate(LocalDateTime.now());
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Voting Session is closed!");
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        ResponseEntity<Object> response = voteController.createNewVote(voteCreateDto);
        Assertions.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteUserNotFoundTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        ResponseEntity<Object> response = voteController.createNewVote(voteCreateDto);
        Assertions.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteUserCannotVoteTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Agenda agenda = buildAgenda();
        ArrayList<Agenda> agendaList = new ArrayList<>();
        agendaList.add(agenda);
        User user = buildUser();
        user.setAgenda(agendaList);
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("The user cannot vote on the agenda he created.");

        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        when(userRepository.findById(voteCreateDto.getUserId())).thenReturn(Optional.of(user));

        ResponseEntity<Object> response = voteController.createNewVote(voteCreateDto);

        Assertions.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());
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
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("User already voted!");
        when(agendaRepository.findById(voteCreateDto.getAgendaId())).thenReturn(Optional.of(agenda));
        when(userRepository.findById(voteCreateDto.getUserId())).thenReturn(Optional.of(user));
        ResponseEntity<Object> response = voteController.createNewVote(voteCreateDto);
        Assertions.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());
    }

}
