package com.todolist2.dto.userDto;

import lombok.Getter;

@Getter
public class CreateUserRequestDto {
    private String userName;
    private String email;

    public CreateUserRequestDto(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
