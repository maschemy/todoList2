package com.todolist2.service;

import com.todolist2.dto.*;
import com.todolist2.entity.Schedule;
import com.todolist2.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponseDto save(CreateScheduleRequestDto request) {
        Schedule schedule = new Schedule(
                request.getId(),
                request.getAuthor(),
                request.getTitle(),
                request.getContents()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponseDto(
                savedSchedule.getAuthor(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetOneScheduleResponseDto> findAll(String author) {
        List<Schedule> schedules;

        if (author != null) {
            schedules = scheduleRepository.findAllByName(author);
        } else {
            schedules = scheduleRepository.findAll();
        }
        return schedules.stream()
                .map(GetOneScheduleResponseDto::from)
                .toList();
    }


    @Transactional(readOnly = true)
    public GetOneScheduleResponseDto findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("일정이 존재하지 않습니다.")
        );
        return GetOneScheduleResponseDto.from(schedule);
    }

    @Transactional
    public UpdateScheduleResponseDto updateOne(UpdateScheduleRequestDto request, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("일정이 존재하지 않습니다.")
        );
        schedule.update(
                request.getTitle(),
                request.getContents()
        );

        return UpdateScheduleResponseDto.from(schedule);
    }
}
