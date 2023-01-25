package com.api.votingsession.controller;

import com.api.votingsession.Repository.AgendaRepository;
import com.api.votingsession.application.service.AgendaService;
import com.api.votingsession.domain.Enum.AgendaTopic;
import com.api.votingsession.domain.Enum.VoteOption;
import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
public class AgendaControllerTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    @Spy
    private AgendaController agendaController;

    @Mock
    private AgendaService agendaService;

    @Mock
    private AgendaRepository agendaRepository;

    private static final String BASE_PATH = "http://localhost:8080/agenda";

    private static final AgendaTopic BASE_AGENDA_TOPIC = AgendaTopic.generateRandomTopic();

    private static final String TEST_TITLE = generateRandomString();

    private static final String TEST_DESCRIPTION = generateRandomString();

    private static final LocalDateTime TEST_REGISTRATION_DATE = LocalDateTime.now();

    private static final String TEST_NAME = generateRandomString();

    private static final VoteOption TEST_VOTE = VoteOption.generateRandomVoteOption();

    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

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

    @Test
    public void getAllAgendasTest() {
        // arrange
        Pageable pageable = PageRequest.of(0, 1);
        List<Agenda> agendaList = Arrays.asList(buildAgenda(), buildAgenda());
        Page<Agenda> agendaPage = new PageImpl<>(agendaList, pageable, agendaList.size());
        ResponseEntity<Page<Agenda>> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(agendaPage);

        // setup mockito behavior
        Mockito.when(agendaRepository.findAll(pageable)).thenReturn(agendaPage);

        // action
        ResponseEntity<Page<Agenda>> response = agendaController.getAllAgendas(pageable);

        // assertions
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void getAgendaByIdSuccess() {
        Agenda agenda = buildAgenda();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(Optional.of(agenda));
        Mockito.when(agendaRepository.findById(agenda.getId())).thenReturn(Optional.of(agenda));
        ResponseEntity<Object> response = agendaController.getAgendaById(agenda.getId());
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void getAgendaByIdNotFound() {
        Agenda agenda = buildAgenda();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda not found!");
        Mockito.when(agendaRepository.findById(agenda.getId())).thenReturn(Optional.empty());
        ResponseEntity<Object> response = agendaController.getAgendaById(agenda.getId());
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewAgendaTest() {
        AgendaCreateDto agendaCreateDto = buildAgendaCreateDto();
        Agenda agenda = buildAgenda();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(agenda);
        Mockito.when(agendaService.createNewAgenda(agendaCreateDto)).thenReturn(expectedResponse);
        ResponseEntity<Object> response = agendaController.createNewAgenda(agendaCreateDto);
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void updateAgendaByIdTest() {
        UUID id = UUID.randomUUID();
        AgendaCreateDto agendaCreateDto = buildAgendaCreateDto();
        Agenda agenda = buildAgenda();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(agenda);
        Mockito.when(agendaService.updateAgendaById(id, agendaCreateDto)).thenReturn(expectedResponse);
        ResponseEntity<Object> response = agendaController.updateAgendaById(id, agendaCreateDto);
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void removeAgendaByIdTest() {
        UUID id = UUID.randomUUID();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body("Agenda deleted successfully!");
        Mockito.when(agendaService.removeAgendaById(id)).thenReturn(expectedResponse);
        ResponseEntity<Object> response = agendaController.removeAgendaById(id);
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

}
