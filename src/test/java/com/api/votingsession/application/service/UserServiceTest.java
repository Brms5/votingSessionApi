package com.api.votingsession.application.service;

import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

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

    private UserCreateDto buildUserCreateDto() {
        return UserCreateDto.builder().name(RandomStringUtils.randomAlphanumeric(10)).build();
    }

    @Test
    public void createNewUserTest() {
        UserCreateDto userCreateDto = buildUserCreateDto();
//        Mockito.when(userRepository.save(agenda)).thenReturn(agenda);
        userService.CreateNewUser(userCreateDto);
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
}