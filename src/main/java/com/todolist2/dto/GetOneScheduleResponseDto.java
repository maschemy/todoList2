package com.todolist2.dto;

import com.todolist2.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneScheduleResponseDto {
    private final Long id;
    private final String author;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetOneScheduleResponseDto(Long id, String author, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static GetOneScheduleResponseDto from(Schedule schedule){
        return new GetOneScheduleResponseDto(
                schedule.getId(),
                schedule.getAuthor(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
