package com.todolist2.dto.scheduleDto;


import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    @Size(min = 1)
    private String title;

    @Size(max = 300, message = "300자 이상 쓰실수 없습니다.")
    private String contents;
}
