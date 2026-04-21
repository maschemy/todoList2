package com.todolist2.dto;


import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private Long id;
    private String Author;
    private String title;
    private String contents;
}
