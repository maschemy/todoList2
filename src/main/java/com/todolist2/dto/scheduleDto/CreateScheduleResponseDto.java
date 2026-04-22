package com.todolist2.dto.scheduleDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponseDto {
    private final String userName;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CreateScheduleResponseDto(String userName, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
