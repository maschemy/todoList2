package com.todolist2.service;

import com.todolist2.config.PasswordEncoder;
import com.todolist2.dto.userDto.*;
import com.todolist2.entity.User;
import com.todolist2.exception.NotFoundException;
import com.todolist2.exception.UnauthorizedException;
import com.todolist2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponseDto save(CreateUserRequestDto request) {
        User user = new User(
                request.getUserName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        return CreateUserResponseDto.from(savedUser);
    }

    @Transactional(readOnly = true)
    public List<GetUserResponseDto> findAll() {
        List<User> user;

        user = userRepository.findAll();
        return user.stream()
                .map(GetUserResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public GetUserResponseDto findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저가 존재하지 않습니다.")
        );

        return GetUserResponseDto.from(user);
    }

    @Transactional
    public UpdateUserResponseDto updateOne(UpdateUserRequestDto request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저가 존재하지 않습니다.")
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
                () -> new NotFoundException("유저가 존재하지 않습니다.")
        );

        userRepository.delete(user);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.")
        );
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        return LoginResponseDto.from(user);
    }
}
