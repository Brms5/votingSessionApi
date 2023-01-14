package com.api.votingsession.application.service;

import com.api.votingsession.application.Interface.IUserService;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User CreateNewUser(UserCreateDto userCreateDto) {
        var user = new User();
        BeanUtils.copyProperties(userCreateDto, user);
        return userRepository.save(user);
    }

    public ResponseEntity<Object> GetUserById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.<ResponseEntity<Object>>map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!"));
    }
}
