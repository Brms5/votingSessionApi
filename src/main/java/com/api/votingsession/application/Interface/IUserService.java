package com.api.votingsession.application.Interface;

import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;

import java.util.Optional;

public interface IUserService {
    User createNewUser(UserCreateDto userCreateDto);
    User updateUserById(UserCreateDto userCreateDto, Optional<User> user);
}
