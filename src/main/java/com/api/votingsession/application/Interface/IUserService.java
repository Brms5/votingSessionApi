package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    User createNewUser(UserCreateDto userCreateDto);
    Optional<User> getUserById(UUID id);
    User updateUserById(UUID id, UserCreateDto userCreateDto);
}
