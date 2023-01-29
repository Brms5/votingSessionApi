package com.api.votingsession.controller;

import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.Utility.ResponsePageable.CustomPage;
import com.api.votingsession.application.service.UserService;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;
import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    @Spy
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

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
    public void getAllUsersTest() {
        // arrange
        Pageable pageable = PageRequest.of(0, 1);
        List<User> userList = Arrays.asList(buildUser(), buildUser());
        Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());
        ResponseEntity<CustomPage<User>> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(new CustomPage<>(userPage));

        // setup mockito behavior
        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);

        // action
        ResponseEntity<CustomPage<User>> response = userController.getAllUsers(pageable);

        // assertions
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void createNewUserTest() {
        UserCreateDto userCreateDto = buildUserCreateDto();
        User user = buildUser();
        ResponseEntity<User> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(user);
        Mockito.when(userService.createNewUser(userCreateDto)).thenReturn(user);
        ResponseEntity<User> response = userController.createNewUser(userCreateDto);
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void getUserByIdSuccessTest() {
        User user = buildUser();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(Optional.of(user));
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        ResponseEntity<Object> response = userController.getUserById(user.getId());
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void getUserByIdNotFoundTest() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        ResponseEntity<Object> response = userController.getUserById(UUID.randomUUID());
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void updateUserByIdSuccessTest() {
        UserCreateDto userCreateDto = buildUserCreateDto();
        User user = buildUser();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(user);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userService.updateUserById(userCreateDto, Optional.of(user))).thenReturn(user);
        ResponseEntity<Object> response = userController.updateUserById(user.getId(), userCreateDto);
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    public void updateUserByIdNotFoundTest() {
        UserCreateDto userCreateDto = buildUserCreateDto();
        User user = buildUser();
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        ResponseEntity<Object> response = userController.updateUserById(user.getId(), userCreateDto);
        Assert.assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        Assert.assertEquals(expectedResponse.getBody(), response.getBody());
    }

}
