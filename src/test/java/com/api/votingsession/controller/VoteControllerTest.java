package com.api.votingsession.controller;

import com.api.votingsession.application.service.VoteService;
import com.api.votingsession.domain.Enum.AgendaTopic;
import com.api.votingsession.domain.Enum.VoteOption;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.dto.ResultVoteDto;
import com.api.votingsession.domain.dto.VoteCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class VoteControllerTest {

    @InjectMocks
    @Spy
    private VoteController voteController;

    @Mock
    private VoteService voteService;

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
                .votingClosedDate(TEST_REGISTRATION_DATE.plusMinutes(10))
                .build();
    }

    private VoteCreateDto buildVoteCreateDto() {
        Agenda agenda = buildAgenda();
        User user = buildUser();
        return VoteCreateDto.builder().agendaId(agenda.getId()).userId(user.getId()).vote(TEST_VOTE).build();
    }

    @Test
    public void getAllVotesByAgendaTest() {
        UUID id = UUID.randomUUID();
        Integer randomNumber = new Random().nextInt((9) + 1);
        ResultVoteDto resultVoteDto = new ResultVoteDto();
        resultVoteDto.setTitle(generateRandomString());
        resultVoteDto.setVoteYes(randomNumber);
        resultVoteDto.setVoteNo(randomNumber);
        ResponseEntity<ResultVoteDto> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(resultVoteDto);
        Mockito.when(voteService.getAllVotesByAgenda(id)).thenReturn(resultVoteDto);
        ResponseEntity<ResultVoteDto> response = voteController.getAllVotesByAgenda(id);
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewVoteTest() {
        VoteCreateDto voteCreateDto = buildVoteCreateDto();
        Vote vote = buildVote();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(vote);
        Mockito.when(voteService.createNewVote(voteCreateDto)).thenReturn(vote);
        ResponseEntity<Vote> response = voteController.createNewVote(voteCreateDto);
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

}
