package com.api.votingsession.application.service;

import com.api.votingsession.application.Interface.IUserService;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import com.api.votingsession.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createNewUser(UserCreateDto userCreateDto) {
        var user = new User();
        user.setName(userCreateDto.getName());
        List<Agenda> agendaList = new ArrayList<>();
        user.setAgenda(agendaList);
        return userRepository.save(user);
    }

    public ResponseEntity<Object> getUserById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.<ResponseEntity<Object>>map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!"));
    }

    public ResponseEntity<Object> updateUserById(UUID id, UserCreateDto userCreateDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");

        User userUpdated = new User(userOptional.get().getId(), userCreateDto.getName(), userOptional.get().getAgenda());
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userUpdated));
    }
}
