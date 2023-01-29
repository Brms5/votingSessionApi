package com.api.votingsession.application.service;

import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
public class UserServiceTest {

    @InjectMocks
    @Spy
    private UserService userService;

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

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
        // arrange
        UserCreateDto userCreateDto = buildUserCreateDto();
        User user = buildUser();

        // setup mockito behavior
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        // action
        User response = userService.createNewUser(userCreateDto);

        // assertions
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User userSaved = userArgumentCaptor.getValue();
        Assertions.assertThat(userSaved.getName()).isNotNull();
        Assertions.assertThat(userSaved.getAgenda()).isNotNull();
        Assert.assertEquals(user, response);
    }

    @Test
    public void updateUserByIdTest() {
        // arrange
        UserCreateDto userCreateDto = buildUserCreateDto();
        User user = buildUser();

        // setting mock behavior
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        // action
        User response = userService.updateUserById(userCreateDto, Optional.of(user));

        // assertions
        Assert.assertEquals(user, response);
    }
}