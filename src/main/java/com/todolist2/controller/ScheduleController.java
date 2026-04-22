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

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

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


    @GetMapping
    public ResponseEntity<List<GetOneScheduleResponseDto>> getAll(@RequestParam (required = false) String userName){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(userName));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponseDto> getOne(@PathVariable Long scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

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
