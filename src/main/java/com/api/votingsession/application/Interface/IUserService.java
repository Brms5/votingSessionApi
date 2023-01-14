package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IUserService {
    User CreateNewUser(UserCreateDto userCreateDto);
    ResponseEntity<Object> GetUserById(UUID id);
}
