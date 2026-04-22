package com.todolist2.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequestDto {
    private String userName;
    private String email;

    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    @NotBlank
    private String password;
}
