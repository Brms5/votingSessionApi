package com.api.votingsession.Application.Service;

import com.api.votingsession.Application.Interface.IUserService;
import com.api.votingsession.Domain.Dto.UserCreateDto;
import com.api.votingsession.Domain.Model.User;
import com.api.votingsession.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public User CreateNewUser(UserCreateDto userCreateDto){

        var user = new User();
        BeanUtils.copyProperties(userCreateDto, user);

        return userRepository.save(user);
    }

    public Page<User> GetAllUsers(Pageable pageable) { return userRepository.findAll(pageable); }

    public ResponseEntity<Object> GetUserById(UUID id) {

        Optional<User> userOptional= userRepository.findById(id);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
    }
}
