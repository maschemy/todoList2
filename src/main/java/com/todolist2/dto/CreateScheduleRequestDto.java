package com.todolist2.dto;


import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private String author;
    private String title;
    private String contents;
}
