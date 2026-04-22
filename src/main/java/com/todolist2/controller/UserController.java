package com.todolist2.controller;

import com.todolist2.dto.userDto.CreateUserRequestDto;
import com.todolist2.dto.userDto.CreateUserResponseDto;
import com.todolist2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponseDto> create(@RequestBody CreateUserRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

}
