package com.todolist2.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    private String Author;
    private String title;
    private String contents;
}
