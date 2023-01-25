package com.api.votingsession.controller;

import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.Utility.ResponsePageable.CustomPage;
import com.api.votingsession.application.service.UserService;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    @ApiOperation(value = "Request all Users", notes = "Search for all Users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")
    })
    public ResponseEntity<CustomPage<User>> getAllUsers(@ApiIgnore @PageableDefault(page = 0, size = 10,
            sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomPage<>(userRepository.findAll(pageable)));
    }

    @PostMapping
    @ApiOperation(value = "Create new user", notes = "Create a user to vote on user's agendas and create your own agendas.")
    public ResponseEntity<User> createNewUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(userCreateDto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Request user by ID", notes = "Search for a specific user")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") UUID id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update user by ID", notes = "Update a specific user")
    public ResponseEntity<Object> updateUserById(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserCreateDto userCreateDto) {
        return userService.updateUserById(id, userCreateDto);
    }

}
