package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IUserService {
    User createNewUser(UserCreateDto userCreateDto);
    ResponseEntity<Object> getUserById(UUID id);
    ResponseEntity<Object> updateUserById(UUID id, UserCreateDto userCreateDto);
}
