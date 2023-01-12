package com.api.votingsession.application.service;

import com.api.votingsession.domain.dto.AgendaCreateDto;
import com.api.votingsession.domain.Enum.AgendaTopic;
import com.api.votingsession.domain.Enum.VoteOption;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.domain.model.Vote;
import com.api.votingsession.Repository.AgendaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
public class AgendaServiceTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    @Spy
    private AgendaService agendaService;

    @Mock
    private AgendaRepository agendaRepository;

    @Captor
    ArgumentCaptor<Agenda> agendaArgumentCaptor;

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

    private MockHttpServletResponse performPost(Agenda agenda) throws Exception {
        return mvc.perform(post(String.format(BASE_PATH)).content(mapper.writeValueAsString(agenda))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
    }

    private MockHttpServletResponse performGet(UUID id) throws Exception {
        return mvc.perform(get(String.format(BASE_PATH))).andReturn().getResponse();
    }

    @Test
    @DisplayName("Create agenda successfully.")
    public void createAgendaSuccessTest() {
        // arrange
//        Agenda agenda = buildAgenda();
        AgendaCreateDto agendaCreateDto = buildAgendaCreateDto();
//        Mockito.when(agendaRepository.save(agenda)).thenReturn(agenda);

        // action
        agendaService.CreateNewAgenda(agendaCreateDto);

        // assertions
        Mockito.verify(agendaRepository).save(agendaArgumentCaptor.capture());
        Agenda agendaSaved = agendaArgumentCaptor.getValue();
        Assertions.assertThat(agendaSaved.getVotes()).isNotNull();
        Assertions.assertThat(agendaSaved.getRegistrationDate()).isNotNull();
        Assertions.assertThat(agendaSaved.getVotingClosedDate()).isNotNull();
    }

    @Test
    public void getAgendaByIdSuccess() {
        Agenda agenda = buildAgenda();
        Mockito.when(agendaRepository.findById(agenda.getId())).thenReturn(Optional.of(agenda));
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        Assert.assertEquals(response.getStatusCode(), agendaService.GetAgendaById(agenda.getId()).getStatusCode());
    }

    @Test
    public void getAgendaByIdNotFound() {
        // arrange
        Agenda agenda = buildAgenda();
//        Mockito.when(agendaRepository.findById(UUID.randomUUID())).thenReturn(Optional.of(agenda));
        ResponseEntity<Object> responseError = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //action
        HttpStatus response = agendaService.GetAgendaById(agenda.getId()).getStatusCode();

        // assertions
        Assert.assertEquals(responseError.getStatusCode(), response);
    }


}
