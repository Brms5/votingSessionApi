package com.api.votingsession.Application.Interface;

import com.api.votingsession.Domain.Dto.UserCreateDto;
import com.api.votingsession.Domain.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IUserService {
    User CreateNewUser(UserCreateDto userCreateDto);
    Page<User> GetAllUsers(Pageable pageable);
    ResponseEntity<Object> GetUserById(UUID id);
}
