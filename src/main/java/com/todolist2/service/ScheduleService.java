package com.todolist2.service;

import com.todolist2.dto.scheduleDto.*;
import com.todolist2.entity.Schedule;
import com.todolist2.entity.User;
import com.todolist2.repository.ScheduleRepository;
import com.todolist2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponseDto save(CreateScheduleRequestDto request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        Schedule schedule = new Schedule(
                user,
                request.getTitle(),
                request.getContents()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponseDto(
                savedSchedule.getUser().getUserName(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetOneScheduleResponseDto> findAll(String userName) {
        List<Schedule> schedules;

        if (userName != null) {
            schedules = scheduleRepository.findAllByUser_UserName(userName);
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
                () -> new IllegalArgumentException("일정이 존재하지 않습니다.")
        );
        return GetOneScheduleResponseDto.from(schedule);
    }

    @Transactional
    public UpdateScheduleResponseDto updateOne(UpdateScheduleRequestDto request, Long scheduleId, String email) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("일정이 존재하지 않습니다.")
        );
        if(!schedule.getUser().getEmail().equals((email))){
            throw new IllegalArgumentException("본인이 작성한 일정만 수정할 수 있습니다.");
        }

        schedule.update(
                request.getTitle(),
                request.getContents()
        );

        return UpdateScheduleResponseDto.from(schedule);
    }

    @Transactional
    public void deleteOne(Long scheduleId, String email) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("일정이 존재하지 않습니다.")
        );

        if(!schedule.getUser().getEmail().equals(email)){
            throw new IllegalArgumentException("본인이 작성한 일정만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
    }


}
