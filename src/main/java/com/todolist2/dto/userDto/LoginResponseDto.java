package com.todolist2.dto.userDto;

import com.todolist2.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final Long id;
    private final String email;
    private final String userName;

    public LoginResponseDto(Long id, String email, String userName) {
        this.id = id;
        this.email = email;
        this.userName = userName;
    }

    public static LoginResponseDto from(User user){
        return new LoginResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUserName()
        );
    }
}
