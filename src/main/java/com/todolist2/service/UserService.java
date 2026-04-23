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

    /**
     * 유저 정보 전달(회원가입)
     * @param request
     * @return
     */
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

    /**
     * 유저 전체 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<GetUserResponseDto> findAll() {
        List<User> user;

        user = userRepository.findAll();
        return user.stream()
                .map(GetUserResponseDto::from)
                .toList();
    }

    /**
     * 유저 단건 조회, userId를 확인해 해당 유저가 존재하는지 체크
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetUserResponseDto findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저가 존재하지 않습니다.")
        );

        return GetUserResponseDto.from(user);
    }

    /**
     * 유저 정보 수정 기능, 유저가 존재하는지 체크
     * @param request
     * @param userId
     * @return
     */
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

    /**
     * 유저 삭제 기능
     * @param userId
     */
    @Transactional
    public void deleteOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저가 존재하지 않습니다.")
        );

        userRepository.delete(user);
    }

    /**
     * 유저 로그인 기능(이메일, 비밀번호 비교)
     * @param request
     * @return
     */
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
