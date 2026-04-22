package com.todolist2.exception;

import org.springframework.http.HttpStatus;

/**
 * 이메일, 비밀번호 등 정보가 맞지않아 인증이 안될때
 */
public class UnauthorizedException extends CustomException{
    public UnauthorizedException(String message){
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
