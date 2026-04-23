package com.todolist2.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequestDto {

    @NotBlank
    @Size(min = 2, max = 6, message = "닉네임은 2글자 이상 6글자 이하여야 합니다.")
    private String userName;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    @NotBlank
    private String password;
}
