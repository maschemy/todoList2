package com.todolist2.controller;


import com.todolist2.dto.scheduleDto.*;
import com.todolist2.service.ScheduleService;
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
    public ResponseEntity<CreateScheduleResponseDto> create(@RequestBody CreateScheduleRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
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
            @RequestBody UpdateScheduleRequestDto request,
            @PathVariable Long scheduleId
            ){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateOne(request, scheduleId));
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long scheduleId){
        scheduleService.deleteOne(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
