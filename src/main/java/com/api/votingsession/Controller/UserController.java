package com.api.votingsession.Controller;

import com.api.votingsession.Application.Service.UserService;
import com.api.votingsession.Domain.Dto.UserCreateDto;
import com.api.votingsession.Domain.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> CreateNewUser(@RequestBody @Valid UserCreateDto userCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.CreateNewUser(userCreateDto));
    }

    @GetMapping
    public ResponseEntity<Page<User>> GetAllUsers(@PageableDefault(page = 0, size = 10,
                                        sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.GetAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> GetAgendaById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.GetUserById(id));
    }

}
