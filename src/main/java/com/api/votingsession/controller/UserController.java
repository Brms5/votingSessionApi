package com.api.votingsession.controller;

import com.api.votingsession.Repository.UserRepository;
import com.api.votingsession.application.service.UserService;
import com.api.votingsession.domain.dto.UserCreateDto;
import com.api.votingsession.domain.model.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<User>> getAllUsers(@ApiIgnore @PageableDefault(page = 0, size = 10,
            sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll(pageable));
    }
    @PostMapping
    @ApiOperation(value = "Create new user", notes = "Search for all users")
    public ResponseEntity<User> createNewUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.CreateNewUser(userCreateDto));
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Request user by ID", notes = "Search for a specific user")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") UUID id) {
        return userService.GetUserById(id);
    }

}
