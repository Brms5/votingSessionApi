package com.api.votingsession.application.service;

import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.application.Interface.IUserService;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.Agenda;
import com.api.votingsession.domain.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public User updateUserById(UserCreateDto userCreateDto, Optional<User> user) {
        User userUpdated = new User(user.get().getId(), userCreateDto.getName(), user.get().getAgenda());
        return userRepository.save(userUpdated);
    }
}
