package com.todolist2.dto.scheduleDto;


import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private Long userId;
    private String title;
    private String contents;
}
