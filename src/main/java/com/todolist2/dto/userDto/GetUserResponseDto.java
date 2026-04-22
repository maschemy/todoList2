package com.todolist2.dto.userDto;

import com.todolist2.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserResponseDto {
    private final Long id;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetUserResponseDto(Long id, String username, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static GetUserResponseDto from(User user){
        return new GetUserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
