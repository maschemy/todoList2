package com.todolist2.service;

import com.todolist2.dto.userDto.CreateUserRequestDto;
import com.todolist2.dto.userDto.CreateUserResponseDto;
import com.todolist2.entity.User;
import com.todolist2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponseDto createUser(CreateUserRequestDto request) {
        User user = new User(
                request.getUserName(),
                request.getEmail()
        );

        User savedUser = userRepository.save(user);

        return CreateUserResponseDto.from(savedUser);
    }
}
