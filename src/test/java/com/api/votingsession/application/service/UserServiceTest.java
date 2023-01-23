package com.api.votingsession.application.service;

import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
public class UserServiceTest {

    @InjectMocks
    @Spy
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    private static String generateRandomString() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private UserCreateDto buildUserCreateDto() {
        return UserCreateDto.builder().name(RandomStringUtils.randomAlphanumeric(10)).build();
    }

    private User buildUser() {
        return User.builder().id(UUID.randomUUID()).name(generateRandomString()).agenda(new ArrayList<>()).build();
    }

    @Test
    public void createNewUserTest() {
        UserCreateDto userCreateDto = buildUserCreateDto();
//        Mockito.when(userRepository.save(agenda)).thenReturn(agenda);
        userService.createNewUser(userCreateDto);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User userSaved = userArgumentCaptor.getValue();
        Assertions.assertThat(userSaved.getId()).isNotNull();
        Assertions.assertThat(userSaved.getName()).isNotNull();
    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void updateUserByIdTest() {
        // arrange
        UserCreateDto userCreateDto = buildUserCreateDto();
        User user = buildUser();
        HttpStatus expectedResponse = HttpStatus.OK;

        // setting mock behavior
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // action
        HttpStatus response = userService.updateUserById(user.getId(), userCreateDto).getStatusCode();

        // assertions
        Assert.assertEquals(expectedResponse, response);
    }
}