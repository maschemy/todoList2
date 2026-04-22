package com.todolist2.dto.userDto;

import com.todolist2.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String email;
    private final String userName;

    public LoginResponseDto(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public static LoginResponseDto from(User user){
        return new LoginResponseDto(
                user.getEmail(),
                user.getUserName()
        );
    }
}
