package com.todolist2.controller;


import com.todolist2.dto.scheduleDto.*;
import com.todolist2.dto.userDto.LoginResponseDto;
import com.todolist2.entity.Schedule;
import com.todolist2.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일정 관리 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 일정 생성 기능, 로그인 되어있는지 확인
     * @param request
     * @param session
     * @return
     */
    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> create(
            @Valid
            @RequestBody CreateScheduleRequestDto request,
            HttpSession session
            ){
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        if(loginUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request, loginUser.getEmail()));
    }


    /**
     * 일정 전체 조회
     * @param userName
     * @return
     */
    @GetMapping
    public ResponseEntity<List<GetOneScheduleResponseDto>> getAll(@RequestParam (required = false) String userName){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(userName));
    }

    /**
     * 일정 단건 조회,scheduleId로 조회
     * @param scheduleId
     * @return
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponseDto> getOne(@PathVariable Long scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    /**
     * 일정 수정 기능, 로그인 되어있는지 체크 후 service로 넘김
     * @param request
     * @param scheduleId
     * @param session
     * @return
     */
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponseDto> update(
            @Valid
            @RequestBody UpdateScheduleRequestDto request,
            @PathVariable Long scheduleId,
            HttpSession session
            ){
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateOne(request, scheduleId,loginUser.getEmail()));
    }

    /**
     * 일정 삭제 기능
     * @param scheduleId
     * @param session
     * @return
     */
    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long scheduleId,
            HttpSession session
            ){
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        scheduleService.deleteOne(scheduleId, loginUser.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
