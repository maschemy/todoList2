package com.todolist2.exception;


import org.springframework.http.HttpStatus;

/**
 * 유저나 일정이 존재하지 않을 때 발생하는 예외처리
 */
public class NotFoundException extends CustomException{
    public NotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
