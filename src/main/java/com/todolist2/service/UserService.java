package com.todolist2.service;

import com.todolist2.dto.userDto.*;
import com.todolist2.entity.User;
import com.todolist2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public List<GetUserResponseDto> findAll() {
        List<User> user;

        user = userRepository.findAll();
        return user.stream()
                .map(GetUserResponseDto::from)
                .toList();
    }

    @Transactional
    public GetUserResponseDto findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

        return GetUserResponseDto.from(user);
    }

    @Transactional
    public UpdateUserResponseDto updateOne(UpdateUserRequestDto request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
        user.update(
                request.getUserName(),
                request.getEmail()
        );
        return UpdateUserResponseDto.from(user);
    }

    @Transactional
    public void deleteOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

        userRepository.delete(user);
    }
}
