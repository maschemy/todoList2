package com.todolist2.controller;

import com.todolist2.dto.userDto.*;
import com.todolist2.entity.User;
import com.todolist2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDto> create(@Valid @RequestBody CreateUserRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid
            @RequestBody LoginRequestDto request,
            HttpSession session
    ){
        LoginResponseDto loginUser = userService.login(request);
        session.setAttribute("loginUser", loginUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
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
            @Valid
            @RequestBody UpdateUserRequestDto request,
            @PathVariable Long userId,
            HttpSession session
    ){
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        if(loginUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(!loginUser.getId().equals(userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateOne(request,userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId,
            HttpSession session
    ){
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        if(loginUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(!loginUser.getId().equals(userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.deleteOne(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
