package com.todolist2.dto.userDto;

import com.todolist2.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserResponseDto {
    private final Long id;
    private final String userName;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateUserResponseDto(Long id, String userName, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UpdateUserResponseDto from(User user){
        return new UpdateUserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

}
