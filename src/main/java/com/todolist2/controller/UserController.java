package com.todolist2.controller;

import com.todolist2.dto.userDto.*;
import com.todolist2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponseDto> create(@RequestBody CreateUserRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponseDto>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponseDto> getOne(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UpdateUserResponseDto> update(
            @RequestBody UpdateUserRequestDto request,
            @PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateOne(request,userId));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId){
        userService.deleteOne(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
